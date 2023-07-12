package com.charlie.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charlie.domain.usecase.GetImageUseCase
import com.charlie.presentation.model.DetailUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getImageUseCase: GetImageUseCase,
) : ViewModel() {
    private var currentImageId: Int = getImageId(state)

    private val _uiState: MutableLiveData<DetailUiState> = MutableLiveData(DetailUiState.Loading)
    val uiState: LiveData<DetailUiState>
        get() = _uiState

    private val _currentDetailUiModel: MutableLiveData<DetailUiModel> = MutableLiveData()
    val currentDetailUiModel: LiveData<DetailUiModel>
        get() = _currentDetailUiModel

    private val _previousDetailUiModel: MutableLiveData<DetailUiModel?> = MutableLiveData()
    val previousDetailUiModel: LiveData<DetailUiModel?>
        get() = _previousDetailUiModel

    private val _nextDetailUiModel: MutableLiveData<DetailUiModel?> = MutableLiveData()
    val nextDetailUiModel: LiveData<DetailUiModel?>
        get() = _nextDetailUiModel

    val isLoading: StateFlow<Boolean>
        get() = _detailUiState
            .map { it is DetailUiState.Loading }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                false,
            )

    init {
        sendUiState(DetailUiState.Loading)
        setImage()
    }

    fun onClickPrevious() {
        sendUiState(DetailUiState.Loading)
        currentImageId--
        setImage()
    }

    fun onClickNext() {
        sendUiState(DetailUiState.Loading)
        currentImageId++
        setImage()
    }

    private fun setImage() {
        setCurrentDetailImage()
        setPreviousDetailImage()
        setNextDetailImage()
    }

    private fun setCurrentDetailImage() {
        getImageUseCase(id = currentImageId)
            .map { imageModel ->
                imageModel?.let { DetailUiModel(it) }
            }
            .flowOn(Dispatchers.IO)
            .onEach {
                if (it != null) {
                    _currentDetailUiModel.value = it
                    sendUiState(DetailUiState.Success)
                } else {
                    sendUiState(DetailUiState.Fail)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun setPreviousDetailImage() {
        getImageUseCase(id = currentImageId - 1)
            .map { imageModel ->
                imageModel?.let { DetailUiModel(it) }
            }
            .flowOn(Dispatchers.IO)
            .onEach { _previousDetailUiModel.value = it }
            .launchIn(viewModelScope)
    }

    private fun setNextDetailImage() {
        getImageUseCase(id = currentImageId + 1)
            .map { imageModel ->
                imageModel?.let { DetailUiModel(it) }
            }
            .flowOn(Dispatchers.IO)
            .onEach { _nextDetailUiModel.value = it }
            .launchIn(viewModelScope)
    }

    private fun sendUiState(uiState: DetailUiState) {
        when (uiState) {
            DetailUiState.Fail -> {
                _uiState.value = uiState
                _uiState.value = DetailUiState.None
            }

            DetailUiState.None,
            DetailUiState.Loading,
            DetailUiState.Success,
            -> _uiState.value = uiState
        }
    }

    private fun getImageId(state: SavedStateHandle): Int {
        return state[CURRENT_IMAGE_ID] ?: -1
    }

    companion object {
        const val CURRENT_IMAGE_ID = "currentId"
    }
}
