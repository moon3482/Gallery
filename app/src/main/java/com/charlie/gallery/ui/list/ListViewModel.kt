package com.charlie.gallery.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.charlie.gallery.model.ImageItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    state: SavedStateHandle,
    private val model: ListModel,
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
        get() = _uiState.map { it == ListUIState.Fail(1) }.asLiveData()

    init {
        load()
    }

    fun onNextPage() {
        page++
        if (_uiState.value == ListUIState.Success) {
            load()
        }
    }

    fun onReload() {
        _imageList.value = emptyList()
        page = 1
        load()
    }

    private fun load() {
        model.getList(page = page)
            .onStart {
                sendUiState(ListUIState.Loading)
            }
            .onEach {
                _imageList.value = _imageList.value?.plus(it) ?: it
            }
            .onCompletion {
                if (it != null) {
                    sendUiState(ListUIState.Fail(page))
                } else {
                    sendUiState(ListUIState.Success)
                }
            }
            .launchIn(viewModelScope)
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
