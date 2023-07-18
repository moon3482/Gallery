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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getImageUseCase: GetImageUseCase,
) : ViewModel() {
    private var imageId: Int = getImageId(state)

    private val _currentImage: MutableLiveData<DetailUiModel> = MutableLiveData()
    val currentImage: LiveData<DetailUiModel>
        get() = _currentImage

    private val _previousImage: MutableLiveData<DetailUiModel?> = MutableLiveData()
    val previousImage: LiveData<DetailUiModel?>
        get() = _previousImage

    private val _nextImage: MutableLiveData<DetailUiModel?> = MutableLiveData()
    val nextImage: LiveData<DetailUiModel?>
        get() = _nextImage

    private val _uiState: MutableLiveData<DetailUIState> = MutableLiveData(DetailUIState.Loading)
    val uiState: LiveData<DetailUIState>
        get() = _uiState

    val isLoading: LiveData<Boolean>
        get() = _uiState
            .map { it is DetailUIState.Loading }

    init {
        loadImages()
    }

    fun onClickPrevious() {
        imageId--
        loadImages()
    }

    fun onClickNext() {
        imageId++
        loadImages()
    }

    private fun loadImages() {
        sendUiState(DetailUIState.Loading)
        loadImage(
            imageId,
            callback = { _currentImage.value = it },
            onError = { sendUiState(DetailUIState.Fail) },
            onCompletion = { sendUiState(DetailUIState.Success) }
        )
        loadImage(
            imageId + 1,
            callback = { _nextImage.value = it },
            onError = { _nextImage.value = null },
        )
        loadImage(
            imageId - 1,
            callback = { _previousImage.value = it },
            onError = { _previousImage.value = null },
        )
    }

    private fun loadImage(
        id: Int,
        callback: (DetailUiModel) -> Unit,
        onError: (() -> Unit)? = null,
        onCompletion: (() -> Unit)? = null,
    ) {
        getImageUseCase(id = id)
            .map { imageModel -> DetailUiModel(imageModel) }
            .flowOn(Dispatchers.IO)
            .onEach { callback(it) }
            .onCompletion {
                if (it == null)
                    onCompletion?.invoke()
            }
            .catch { onError?.invoke() }
            .launchIn(viewModelScope)
    }

    private fun sendUiState(uiState: DetailUIState) {
        when (uiState) {
            is DetailUIState.Fail -> {
                _uiState.value = uiState
                _uiState.value = DetailUIState.None
            }

            is DetailUIState.None,
            is DetailUIState.Loading,
            is DetailUIState.Success,
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
