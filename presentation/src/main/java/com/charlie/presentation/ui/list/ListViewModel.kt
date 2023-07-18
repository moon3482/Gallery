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

    private val _uiState: MutableLiveData<ListUiState> = MutableLiveData(ListUiState.Loading)
    val uiState: LiveData<ListUiState>
        get() = _uiState

    val isLoading: LiveData<Boolean>
        get() = _uiState.map { it is ListUiState.Loading }

    private val _isFailure: MutableLiveData<Boolean> = MutableLiveData()
    val isFailure: LiveData<Boolean>
        get() = _imageList.map { it.isEmpty() }


    init {
        loadImageList()
    }

    fun onNextPage() {
        page++
        if (_uiState.value is ListUiState.Success) {
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
                sendUiState(ListUiState.Loading)
            }
            .map { imageModelList ->
                imageModelList.map { ListItemUiModel(it) }
            }
            .onEach {
                _imageList.value = _imageList.value?.plus(it) ?: it
            }
            .onCompletion {
                if (it != null) {
                    sendUiState(ListUiState.Fail(page))
                } else {
                    sendUiState(ListUiState.Success)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun sendUiState(uiState: ListUiState) {
        when (uiState) {
            is ListUiState.Fail -> {
                _uiState.value = uiState
                _uiState.value = ListUiState.None
            }

            is ListUiState.Loading,
            is ListUiState.None,
            is ListUiState.Success,
            -> _uiState.value = uiState
        }
    }
}
