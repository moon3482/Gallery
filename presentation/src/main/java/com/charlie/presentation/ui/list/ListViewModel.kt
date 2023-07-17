package com.charlie.presentation.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.charlie.domain.usecase.GetImageListUseCase
import com.charlie.presentation.model.ListItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getImageListUseCase: GetImageListUseCase,
) : ViewModel() {
    private var page = 1

    private val _imageList: MutableLiveData<List<ListItemUiModel>> = MutableLiveData()
    val imageList: LiveData<List<ListItemUiModel>>
        get() = _imageList

    private val _uiState: MutableLiveData<ListUIState> = MutableLiveData(ListUIState.Loading)
    val uiState: LiveData<ListUIState>
        get() = _uiState

    val isLoading: LiveData<Boolean>
        get() = _uiState.map { it is ListUIState.Loading }

    private val _isFailure: MutableLiveData<Boolean> = MutableLiveData()
    val isFailure: LiveData<Boolean>
        get() = _imageList.map { it.isEmpty() }


    init {
        loadImageList()
    }

    fun onNextPage() {
        page++
        if (_uiState.value is ListUIState.Success) {
            loadImageList()
        }
    }

    fun onReload() {
        _isFailure.value = false
        _imageList.value = emptyList()
        page = 1
        loadImageList()
    }

    private fun loadImageList() {
        getImageListUseCase(page)
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
                _uiState.value = uiState
                _uiState.value = ListUIState.None
            }

            is ListUIState.Loading,
            is ListUIState.None,
            is ListUIState.Success,
            -> _uiState.value = uiState
        }
    }
}
