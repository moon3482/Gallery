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

    private val _uiState: MutableStateFlow<ListUiState> = MutableStateFlow(ListUiState.None)
    val isLoading: StateFlow<Boolean>
        get() = _uiState
            .map { it is ListUiState.Loading }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                false,
            )

    private val _imageList: MutableLiveData<List<ListItemUiModel>> = MutableLiveData()
    val imageList: LiveData<List<ListItemUiModel>> get() = _imageList

    init {
        loadList()
    }

    fun loadNextPage() {
        page++
        if (_uiState.value is ListUiState.Success) {
            loadList()
        }
    }

    fun reloadList() {
        page = 1
        _imageList.value = emptyList()
        loadList()
    }

    private fun loadList() {
        getImageListUseCase(page)
            .onStart { sendUiState(ListUiState.Loading) }
            .map { imageModelList ->
                imageModelList.map { ListItemUiModel(it) }
            }
            .onEach { _imageList.value = _imageList.value?.plus(it) ?: it }
            .onCompletion {
                if (it != null)
                    sendUiState(ListUiState.Fail(page))
                else
                    sendUiState(ListUiState.Success)
            }
            .launchIn(viewModelScope)
    }

    private fun sendUiState(uiState: ListUiState) {
        when (uiState) {
            is ListUiState.Fail -> {
                _uiState.tryEmit(uiState)
                _uiState.tryEmit(ListUiState.None)
            }

            ListUiState.Loading,
            ListUiState.None,
            ListUiState.Success,
            -> _uiState.tryEmit(uiState)
        }
    }
}
