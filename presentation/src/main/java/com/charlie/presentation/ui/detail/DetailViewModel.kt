package com.charlie.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.charlie.domain.usecase.GetImageUseCase
import com.charlie.presentation.model.DetailUiModel
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
class DetailViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getImageUseCase: GetImageUseCase,
) : ViewModel() {
    private var imageId = getImageId(state)

    private val _uiState: MutableStateFlow<DetailUiState> = MutableStateFlow(DetailUiState.None)
    val isLoading: StateFlow<Boolean>
        get() = _uiState
            .map { it is DetailUiState.Loading }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                false,
            )

    private val _currentDetailUiModel: MutableLiveData<DetailUiModel?> = MutableLiveData()
    val currentDetailUiModel: LiveData<DetailUiModel?> get() = _currentDetailUiModel

    private val _previousDetailUiModel: MutableLiveData<DetailUiModel?> = MutableLiveData()
    val previousUrl: LiveData<String?> get() = _previousDetailUiModel.map { it?.downloadUrl }
    val isPreviousEnable: LiveData<Boolean> get() = _previousDetailUiModel.map { it != null }

    private val _nextDetailUiModel: MutableLiveData<DetailUiModel?> = MutableLiveData()
    val nextUrl: LiveData<String?> get() = _nextDetailUiModel.map { it?.downloadUrl }
    val isNextEnable: LiveData<Boolean> get() = _nextDetailUiModel.map { it != null }

    init {
        loadImage()
    }

    fun loadPrevious() {
        imageId--
        loadImage()
    }

    fun loadNext() {
        imageId++
        loadImage()
    }

    private fun loadImage() {
        getImageUseCase(imageId)
            .onStart { sendUiState(DetailUiState.Loading) }
            .onEach { _currentDetailUiModel.value = it?.let { DetailUiModel(it) } }
            .onCompletion {
                if (it != null)
                    sendUiState(DetailUiState.Fail)
                else
                    sendUiState(DetailUiState.Success)
            }
            .launchIn(viewModelScope)

        getImageUseCase(imageId - 1)
            .onEach { _previousDetailUiModel.value = it?.let { DetailUiModel(it) } }
            .launchIn(viewModelScope)

        getImageUseCase(imageId + 1)
            .onEach { _nextDetailUiModel.value = it?.let { DetailUiModel(it) } }
            .launchIn(viewModelScope)
    }

    private fun sendUiState(uiState: DetailUiState) {
        when (uiState) {
            DetailUiState.Fail -> {
                _uiState.tryEmit(uiState)
                _uiState.tryEmit(DetailUiState.None)
            }

            DetailUiState.None,
            DetailUiState.Loading,
            DetailUiState.Success,
            -> _uiState.tryEmit(uiState)
        }
    }

    private fun getImageId(state: SavedStateHandle): Int {
        return state[CURRENT_IMAGE_ID] ?: -1
    }

    companion object {
        const val CURRENT_IMAGE_ID = "currentId"
    }
}
