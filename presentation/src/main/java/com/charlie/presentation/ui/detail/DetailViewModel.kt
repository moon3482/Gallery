package com.charlie.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.charlie.domain.usecase.GetImageUseCase
import com.charlie.presentation.model.DetailUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private var currentImageId: Int = state[CURRENT_IMAGE_ID] ?: run { sendUiState(DetailUiState.Fail) }.let { -1 }

    private val _detailUiState: MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState>
        get() = _detailUiState.asStateFlow()

    private val _imageDetailDataResponse: MutableLiveData<DetailUIModel> = MutableLiveData()
    val imageDetailDataResponse: LiveData<DetailUIModel>
        get() = _imageDetailDataResponse

    val isLoading: LiveData<Boolean>
        get() = _detailUiState
            .map { it == DetailUiState.Loading }
            .asLiveData()

    val isEnablePreviousButton: LiveData<Boolean>
        get() = _previousImageUrl.map { it != null }

    val isEnableNextButton: LiveData<Boolean>
        get() = _nextImageUrl.map { it != null }

    val currentImageUrl: LiveData<String>
        get() = _imageDetailDataResponse.map { it.downloadUrl }

    private val _previousImageUrl: MutableLiveData<String?> = MutableLiveData()
    val previousImageUrl: LiveData<String?>
        get() = _previousImageUrl

    private val _nextImageUrl: MutableLiveData<String?> = MutableLiveData()
    val nextImageUrl: LiveData<String?>
        get() = _nextImageUrl

    init {
        setImage()
    }

    fun onClickPrevious() {
        currentImageId--
        setScreen()
    }

    fun onClickNext() {
        currentImageId++
        setScreen()
    }

    private fun setScreen() {
        sendUiState(DetailUiState.Loading)
        getImage(
            id = currentImageId,
            onEach = {
                if (it == null) {
                    sendUiState(DetailUiState.Fail)
                } else {
                    _imageDetailDataResponse.value = it
                }
            },
            onComplete = { sendUiState(DetailUiState.Success) },
        )
        getImage(
            id = currentImageId - 1,
            onEach = {
                _previousImageUrl.value = it?.downloadUrl
            },
        )
        getImage(
            id = currentImageId + 1,
            onEach = {
                _nextImageUrl.value = it?.downloadUrl
            },
        )
    }

    private fun getImage(
        id: Int,
        onEach: (DetailUIModel?) -> Unit,
        onComplete: () -> Unit = {},
    ) {
        getImageUseCase(id = id)
            .map { imageModel ->
                imageModel?.let { DetailUIModel(it) }
            }
            .onEach { onEach(it) }
            .onCompletion { onComplete() }
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

    companion object {
        private const val CURRENT_IMAGE_ID = "currentId"
    }
}
