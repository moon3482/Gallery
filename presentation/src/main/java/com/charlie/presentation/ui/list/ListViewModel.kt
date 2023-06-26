package com.charlie.presentation.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charlie.domain.usecase.GetImageListUseCase
import com.charlie.presentation.model.ListItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getImageListUseCase: GetImageListUseCase,
) : ViewModel() {
    private var page = 1

    private val _imageList: MutableLiveData<List<ListItemUiModel>> = MutableLiveData()
    val imageList: LiveData<List<ListItemUiModel>>
        get() = _imageList

    private val _uiState: MutableStateFlow<ListUIState> = MutableStateFlow(ListUIState.Loading)
    val uiState: StateFlow<ListUIState>
        get() = _uiState.asStateFlow()

    val isLoading: StateFlow<Boolean>
        get() = _uiState
            .map { it is ListUIState.Loading }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = false,
            )

    val isFailure: StateFlow<Boolean>
        get() = _uiState
            .map { it is ListUIState.Fail }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = false,
            )

    init {
        getImageList()
    }

    fun onNextPage() {
        page++
        if (_uiState.value == ListUIState.Success) {
            getImageList()
        }
    }

    fun onReload() {
        _imageList.value = emptyList()
        page = 1
        getImageList()
    }

    private fun getImageList() {
        getImageListUseCase(page = page)
            .onStart {
                sendUiState(ListUIState.Loading)
            }
            .map { imageModelList ->
                imageModelList.map { ListItemUiModel(it) }
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
