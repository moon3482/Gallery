package com.charlie.gallery.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.charlie.gallery.model.ImageItemModel
import com.charlie.gallery.usecase.GetImageListUseCase
import com.charlie.gallery.usecase.UpdateImageListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel(
    private val model: ListModel = ListModel(),
) : ViewModel() {
    private var page = 1

    private val _imageList: MutableLiveData<List<ImageItemModel>> = MutableLiveData()
    val imageList: LiveData<List<ImageItemModel>>
        get() = _imageList

    private val _uiState: MutableStateFlow<ListUIState> = MutableStateFlow(ListUIState.Loading)
    val uiState: StateFlow<ListUIState>
        get() = _uiState.asStateFlow()

    val isLoading: LiveData<Boolean>
        get() = _uiState.map { it == ListUIState.Loading }.asLiveData()

    val isFailure: LiveData<Boolean>
        get() = _uiState.map { it == ListUIState.Fail(page) }.asLiveData()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            getImageListUseCase(page, (page - 10) * limit).collectLatest { entityList ->
                withContext(Dispatchers.Main) {
                    _imageList.value = entityList.map { ImageItemModel(it) }
                }
            }
        }

        load(onSuccess = {
            sendUiState(ListUIState.Success)
        }, onFailure = {
            _uiState.value = ListUIState.Fail(page)
        })
    }

    fun onNextPage() {
        page++
        sendUiState(ListUIState.Loading)
        load(onSuccess = {
            sendUiState(ListUIState.Success)
        }, onFailure = {
            sendUiState(ListUIState.Fail(page))
        })
    }

    fun onReload() {
        page = 1
        sendUiState(ListUIState.Loading)
        load(onSuccess = {
            sendUiState(ListUIState.Success)
        }, onFailure = {
            _uiState.value = ListUIState.Fail(page)
        })
    }

    private fun load(
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        sendUiState(ListUIState.Loading)
        CoroutineScope(Dispatchers.IO).launch {
            updateImageList(
                page,
                onSuccess = onSuccess,
                onFailure = onFailure,
            )
        }
    }

    private fun sendUiState(uiState: ListUIState) {
        when (uiState) {
            is ListUIState.Fail -> {
                _uiState.tryEmit(uiState)
                _uiState.tryEmit(ListUIState.None)
            }

            ListUIState.Loading, ListUIState.None, ListUIState.Success -> {
                _uiState.tryEmit(uiState)
            }
        }

    }
}
