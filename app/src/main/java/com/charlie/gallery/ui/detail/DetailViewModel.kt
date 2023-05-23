package com.charlie.gallery.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.charlie.gallery.model.ImageDetailData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
    private val model: Model,
    private val initId: Int = -1,
) {
    private val _currentId = MutableLiveData<Int>()
    val currentId: LiveData<Int>
        get() = _currentId

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _author: MutableLiveData<String> = MutableLiveData()
    val author: LiveData<String>
        get() = _author

    private val _width: MutableLiveData<String> = MutableLiveData()
    val width: LiveData<String>
        get() = _width

    private val _height: MutableLiveData<String> = MutableLiveData()
    val height: LiveData<String>
        get() = _height

    private val _url: MutableLiveData<String> = MutableLiveData()
    val url: MutableLiveData<String>
        get() = _url

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

    private val _onFailedInit: MutableLiveData<Unit> = MutableLiveData()
    val onFailedInit: LiveData<Unit>
        get() = _onFailedInit

    init {
        if (initId < 0) {
            _onFailedInit.value = Unit
        } else {
            _currentId.value = initId
            setScreen(initId)
        }
    }

    fun onClickPrevious() {
        _currentId.value = _currentId.value?.minus(1) ?: -1
        setScreen(_currentId.value!!)

    }

    fun onClickNext() {
        _currentId.value = _currentId.value?.plus(1) ?: -1
        setScreen(_currentId.value!!)
    }

    private fun setScreen(
        id: Int,
    ) {
        _isLoading.value = true
        load(
            id = id,
            onSuccess = {
                _isLoading.postValue(false)
                _currentImageUrl.postValue(it.downloadUrl)
                _author.postValue(it.author)
                _width.postValue(it.width.toString())
                _height.postValue(it.height.toString())
                _url.postValue(it.url)
            },
            onFailure = {
                _isLoading.postValue(false)
                _currentImageUrl.postValue(null)
            },
        )
        load(
            id = id - 1,
            onSuccess = {
                _isEnablePreviousButton.postValue(true)
                _previousImageUrl.postValue(it.downloadUrl)
            },
            onFailure = {
                _isEnablePreviousButton.postValue(false)
                _previousImageUrl.postValue(null)
            }
        )
        load(
            id = id + 1,
            onSuccess = {
                _isEnableNextButton.postValue(true)
                _nextImageUrl.postValue(it.downloadUrl)
            },
            onFailure = {
                _isEnableNextButton.postValue(false)
                _nextImageUrl.postValue(null)
            }
        )
    }

    private fun load(
        id: Int,
        onSuccess: (ImageDetailData) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching { model.getImageDetailData(id = id) }
                .fold(
                    onSuccess = {
                        onSuccess(it)
                    },
                    onFailure = {
                        onFailure(it)
                    }
                )
        }
    }
}
