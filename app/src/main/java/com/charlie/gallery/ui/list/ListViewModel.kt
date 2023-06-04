package com.charlie.gallery.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.charlie.gallery.model.ImageItemData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel(private val getImageList: ListModel) {

    private var page = 1

    private val _imageList: MutableLiveData<List<ImageItemData>> = MutableLiveData()
    val imageList: LiveData<List<ImageItemData>>
        get() = _imageList

    private val _uiState: MutableStateFlow<ListUIState> = MutableStateFlow(ListUIState.Loading)
    val uiState: StateFlow<ListUIState>
        get() = _uiState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val isLoading: LiveData<Boolean>
        get() = _uiState
            .mapLatest { it == ListUIState.Loading }
            .asLiveData()

    @OptIn(ExperimentalCoroutinesApi::class)
    val isFailure: LiveData<Boolean>
        get() = _uiState
            .mapLatest { it != ListUIState.Loading && it != ListUIState.Success }
            .asLiveData()

    init {
        load(
            onSuccess = {
                _imageList.postValue(it)
            },
            onFailure = {
                _imageList.postValue(emptyList())
            }
        )
    }

    fun onNextPage() {
        page++
        sendUiState(ListUIState.Loading)
        load(
            onSuccess = {
                _imageList.value = (_imageList.value ?: mutableListOf()) + it
            },
            onFailure = {
                sendUiState(ListUIState.Fail(page))
            }
        )
    }

    fun onReload() {
        page = 1
        sendUiState(ListUIState.Loading)
        load(
            onSuccess = {
                _imageList.value = it
            },
            onFailure = {
                _imageList.value = emptyList()
            }
        )
    }

    private fun load(
        onSuccess: (List<ImageItemData>) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        sendUiState(ListUIState.Loading)
        CoroutineScope(Dispatchers.IO).launch {
            runCatching { getImageList(page) }
                .onSuccess {
                    withContext(Dispatchers.Main) {
                        sendUiState(ListUIState.Success)
                        onSuccess(it)
                    }
                }
                .onFailure {
                    withContext(Dispatchers.Main) {
                        sendUiState(ListUIState.Fail(page))
                        onFailure(it)
                    }
                }
        }
    }

    private fun sendUiState(uiState: ListUIState) {
        when (uiState) {
            is ListUIState.Fail -> {
                _uiState.tryEmit(uiState)
                _uiState.tryEmit(ListUIState.None)
            }

            ListUIState.Loading,
            ListUIState.None,
            ListUIState.Success -> {
                _uiState.tryEmit(uiState)
            }
        }

    }
}
