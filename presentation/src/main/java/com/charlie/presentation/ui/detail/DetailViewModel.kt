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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getImageUseCase: GetImageUseCase,
) : ViewModel() {
    private var imageId = getImageId(state)

    private val _currentDetailUiModel: MutableLiveData<DetailUiModel?> = MutableLiveData()
    val currentDetailUiModel: LiveData<DetailUiModel?> get() = _currentDetailUiModel

    private val _previousDetailUiModel: MutableLiveData<DetailUiModel?> = MutableLiveData()
    val previousUrl: LiveData<String?> get() = _previousDetailUiModel.map { previousDetailUiModel -> previousDetailUiModel?.downloadUrl }
    val isPreviousEnable: LiveData<Boolean> get() = _previousDetailUiModel.map { previousDetailUiModel -> previousDetailUiModel != null }

    private val _nextDetailUiModel: MutableLiveData<DetailUiModel?> = MutableLiveData()
    val nextUrl: LiveData<String?> get() = _nextDetailUiModel.map { nextDetailUiModel -> nextDetailUiModel?.downloadUrl }
    val isNextEnable: LiveData<Boolean> get() = _nextDetailUiModel.map { nextDetailUiModel -> nextDetailUiModel != null }

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
            .onEach { currentImageModel ->
                _currentDetailUiModel.value = currentImageModel?.let { imageModel -> DetailUiModel(imageModel) }
            }
            .launchIn(viewModelScope)

        getImageUseCase(imageId - 1)
            .onEach { previousImageModel ->
                _previousDetailUiModel.value = previousImageModel?.let { imageModel -> DetailUiModel(imageModel) }
            }
            .launchIn(viewModelScope)

        getImageUseCase(imageId + 1)
            .onEach { nextImageModel ->
                _nextDetailUiModel.value = nextImageModel?.let { imageModel -> DetailUiModel(imageModel) }
            }
            .launchIn(viewModelScope)
    }

    private fun getImageId(state: SavedStateHandle): Int {
        return state[CURRENT_IMAGE_ID] ?: -1
    }

    companion object {
        const val CURRENT_IMAGE_ID = "currentId"
    }
}
