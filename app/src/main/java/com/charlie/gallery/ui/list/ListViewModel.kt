package com.charlie.gallery.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.charlie.gallery.model.ImageItemData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(private val model: ListModel) {

    private val _initImageList: MutableLiveData<List<ImageItemData>> = MutableLiveData()
    val initImageList: LiveData<List<ImageItemData>>
        get() = _initImageList

    private val _addImageList: MutableLiveData<List<ImageItemData>> = MutableLiveData()
    val addImageList: LiveData<List<ImageItemData>>
        get() = _addImageList

    private val _page: MutableLiveData<Int> = MutableLiveData(1)
    val page: LiveData<Int>
        get() = _page

    private val _onFailToastMessage: MutableLiveData<Unit> = MutableLiveData()
    val onFailToastMessage: LiveData<Unit>
        get() = _onFailToastMessage

    private val _isFailedLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFailedLoading: LiveData<Boolean>
        get() = _isFailedLoading

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        showLoading()
        load(
            onSuccess = {
                _initImageList.postValue(it)
            },
            onFailure = {
                _initImageList.postValue(emptyList())
                _isFailedLoading.postValue(true)
            }
        )
    }

    fun onNextPage() {
        _page.value = _page.value?.plus(1)
        load(
            onSuccess = { _addImageList.postValue(it) },
            onFailure = {
                _addImageList.postValue(emptyList())
                _onFailToastMessage.postValue(Unit)
            }
        )
    }

    fun onReload() {
        showLoading()
        load(
            onSuccess = {
                _initImageList.postValue(it)
            },
            onFailure = {
                _initImageList.postValue(emptyList())
                _isFailedLoading.postValue(true)
            }
        )
    }

    private fun showLoading() {
        _isFailedLoading.value = false
        _isLoading.value = true
    }

    private fun load(
        onSuccess: (List<ImageItemData>) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching { model.getImageList(_page.value ?: 1) }
                .fold(
                    onSuccess = {
                        _isLoading.postValue(false)
                        onSuccess(it)
                    },
                    onFailure = {
                        _isLoading.postValue(false)
                        onFailure(it)
                    }
                )
        }
    }
}