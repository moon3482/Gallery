package com.charlie.gallery.ui.list

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListPresenter(
    private val view: ListContract.View,
    private val model: ListContract.Model,
) : ListContract.Presenter {
    private var currentPage = 1
    override fun start() {
        view.showLoading()
        CoroutineScope(Dispatchers.IO).launch {
            runCatching { model.getImageList(page = currentPage) }
                .onSuccess {
                    withContext(Dispatchers.Main) {
                        view.hideLoading()
                        view.showList(imageItemDataList = it)
                    }
                }
                .onFailure {
                    withContext(Dispatchers.Main) {
                        view.hideLoading()
                        view.showLoadingFailed()
                    }
                }
        }
    }

    override fun onClickItem(currentId: Int) {
        view.showDetailFragment(currentId = currentId)
    }

    override fun onClickReload() {
        view.showLoading()
        CoroutineScope(Dispatchers.IO).launch {
            runCatching { model.getImageList(page = currentPage) }
                .onSuccess {
                    withContext(Dispatchers.Main) {
                        view.hideLoading()
                        view.hiedLoadingFailed()
                        view.showList(imageItemDataList = it)
                    }
                }
                .onFailure {
                    withContext(Dispatchers.Main) {
                        view.hideLoading()
                        view.showLoadingFailed()
                    }
                }
        }
    }

    override fun onNextPage() {
        view.showLoading()
        currentPage++
        CoroutineScope(Dispatchers.IO).launch {
            runCatching { model.getImageList(page = currentPage) }
                .onSuccess {
                    withContext(Dispatchers.Main) {
                        view.hideLoading()
                        view.showNextPage(imageItemDataList = it)
                    }
                }
                .onFailure {
                    withContext(Dispatchers.Main) {
                        view.hideLoading()
                        view.showFailedToast()
                    }
                }
        }
    }
}
