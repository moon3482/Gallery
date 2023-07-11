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
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getImageUseCase: GetImageUseCase,
) : ViewModel() {
    private var currentImageId: Int = getImageId(state)

    private val _detailUiState: MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState>
        get() = _detailUiState.asStateFlow()

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
            .map { it == DetailUiState.Loading }
            .stateIn(viewModelScope, SharingStarted.Lazily, false)


    init {
        setImage()
    }

    fun onClickPrevious() {
        currentImageId--
        setImage()
    }

    fun onClickNext() {
        currentImageId++
        setImage()
    }

    private fun setImage() {
        sendUiState(DetailUiState.Loading)
        getImage(
            id = currentImageId,
            onEach = {
                if (it == null) {
                    sendUiState(DetailUiState.Fail)
                } else {
                    _currentDetailUiModel.value = it
                }
            },
            onComplete = { sendUiState(DetailUiState.Success) },
        )
        getImage(
            id = currentImageId - 1,
            onEach = {
                _previousDetailUiModel.value = it
            },
        )
        getImage(
            id = currentImageId + 1,
            onEach = {
                _nextDetailUiModel.value = it
            },
        )
    }

    private fun getImage(
        id: Int,
        onEach: (DetailUiModel?) -> Unit,
        onComplete: () -> Unit = {},
    ) {
        getImageUseCase(id = id)
            .map { imageModel ->
                imageModel?.let { DetailUiModel(it) }
            }
            .flowOn(Dispatchers.IO)
            .onEach { onEach(it) }
            .onCompletion { onComplete() }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    private fun sendUiState(uiState: DetailUiState) {
        when (uiState) {
            DetailUiState.Fail -> {
                _detailUiState.tryEmit(uiState)
                _detailUiState.tryEmit(DetailUiState.None)
            }

            DetailUiState.None,
            DetailUiState.Loading,
            DetailUiState.Success -> _detailUiState.tryEmit(uiState)
        }
    }

    private fun getImageId(state: SavedStateHandle): Int {
        return state[CURRENT_IMAGE_ID] ?: -1
    }

    companion object {
        const val CURRENT_IMAGE_ID = "currentId"
    }
}
