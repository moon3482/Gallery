package com.charlie.gallery.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.charlie.gallery.model.ImageDetailData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(
    private val getImageDetailData: DetailModel,
    private var currentId: Int,
) {

    private val _detailUiState: MutableStateFlow<DetailUiState> = MutableStateFlow(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState>
        get() = _detailUiState.asStateFlow()

    private val _imageDetailData: MutableLiveData<ImageDetailData> = MutableLiveData()
    val imageDetailData: LiveData<ImageDetailData>
        get() = _imageDetailData

    @OptIn(ExperimentalCoroutinesApi::class)
    val isLoading: LiveData<Boolean>
        get() = _detailUiState
            .mapLatest { it == DetailUiState.Loading }
            .asLiveData()

    val isEnablePreviousButton: LiveData<Boolean>
        get() = _previousImageUrl.map { it != null }

    val isEnableNextButton: LiveData<Boolean>
        get() = _nextImageUrl.map { it != null }

    val currentImageUrl: LiveData<String>
        get() = _imageDetailData.map { it.downloadUrl }

    private val _previousImageUrl: MutableLiveData<String?> = MutableLiveData()
    val previousImageUrl: LiveData<String?>
        get() = _previousImageUrl

    private val _nextImageUrl: MutableLiveData<String?> = MutableLiveData()
    val nextImageUrl: LiveData<String?>
        get() = _nextImageUrl

    init {
        if (currentId < 0) {
            _detailUiState.tryEmit(DetailUiState.Fail)
        } else {
            setScreen()
        }
    }

    fun onClickPrevious() {
        currentId--
        setScreen()
    }

    fun onClickNext() {
        currentId++
        setScreen()
    }

    private fun setScreen() {
        _detailUiState.tryEmit(DetailUiState.Loading)
        load(
            id = currentId,
            onSuccess = {
                _detailUiState.tryEmit(DetailUiState.Success)
                _imageDetailData.value = it
            },
            onFailure = {
                _detailUiState.tryEmit(DetailUiState.Fail)
            },
        )
        load(
            id = currentId - 1,
            onSuccess = {
                _previousImageUrl.value = it.downloadUrl
            },
            onFailure = {
                _previousImageUrl.value = null
            },
        )
        load(
            id = currentId + 1,
            onSuccess = {
                _nextImageUrl.value = it.downloadUrl
            },
            onFailure = {
                _nextImageUrl.value = null
            },
        )
    }

    private fun load(
        id: Int,
        onSuccess: (ImageDetailData) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching { getImageDetailData(id = id) }
                .onSuccess {
                    withContext(Dispatchers.Main) {
                        onSuccess(it)
                    }
                }.onFailure {
                    withContext(Dispatchers.Main) {
                        onFailure(it)
                    }
                }
        }
    }
}
