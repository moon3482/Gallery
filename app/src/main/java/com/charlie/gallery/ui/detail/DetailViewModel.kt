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
    private val model: Model,
    private var currentId: Int = -1,
) {

    private val _detailUiState: MutableStateFlow<DetailUiState> = MutableStateFlow(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState>
        get() = _detailUiState.asStateFlow()

    private val _imageDetailData: MutableLiveData<ImageDetailData> = MutableLiveData()

    @OptIn(ExperimentalCoroutinesApi::class)
    val isLoading: LiveData<Boolean>
        get() = _detailUiState
            .mapLatest { it == DetailUiState.Loading }
            .asLiveData()

    val author: LiveData<String>
        get() = _imageDetailData.map { it.author }

    val width: LiveData<String>
        get() = _imageDetailData.map { it.width.toString() }

    val height: LiveData<String>
        get() = _imageDetailData.map { it.height.toString() }

    val url: LiveData<String>
        get() = _imageDetailData.map { it.url }

    private val _isEnablePreviousButton: MutableLiveData<Boolean> = MutableLiveData(true)
    val isEnablePreviousButton: LiveData<Boolean>
        get() = _isEnablePreviousButton

    private val _isEnableNextButton: MutableLiveData<Boolean> = MutableLiveData(true)
    val isEnableNextButton: LiveData<Boolean>
        get() = _isEnableNextButton

    private val _currentImageUrl: MutableLiveData<String?> = MutableLiveData()
    val currentImageUrl: LiveData<String?>
        get() = _currentImageUrl

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
                _currentImageUrl.value = it.downloadUrl
                _imageDetailData.value = it
            },
            onFailure = {
                _detailUiState.tryEmit(DetailUiState.Fail)
                _currentImageUrl.value = null
            },
        )
        load(
            id = currentId - 1,
            onSuccess = {
                _isEnablePreviousButton.value = true
                _previousImageUrl.value = it.downloadUrl
            },
            onFailure = {
                _isEnablePreviousButton.value = false
                _previousImageUrl.value = null
            },
        )
        load(
            id = currentId + 1,
            onSuccess = {
                _isEnableNextButton.value = true
                _nextImageUrl.value = it.downloadUrl
            },
            onFailure = {
                _isEnableNextButton.value = false
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
            runCatching { model.getImageDetailData(id = id) }
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
