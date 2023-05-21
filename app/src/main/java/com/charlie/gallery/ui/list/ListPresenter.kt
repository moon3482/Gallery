package com.charlie.gallery.ui.list

import com.charlie.gallery.model.ImageItemData
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
        showLoading()
        loadingImageList(
            onSuccess = {
                view.showList(it)
            },
            onFailure = {
                view.showLoadingFailed()
            },
        )
    }

    override fun onClickItem(currentId: Int) {
        view.showDetailFragment(currentId = currentId)
    }

    override fun onClickReload() {
        showLoading()
        loadingImageList(
            onSuccess = {
                view.showList(it)
            },
            onFailure = {
                view.showLoadingFailed()
            },
        )
    }

    override fun onNextPage() {
        showLoading()
        currentPage++
        loadingImageList(
            onSuccess = {
                view.showNextPage(it)
            },
            onFailure = {
                view.showFailedToast()
            },
        )
    }

    private fun loadingImageList(
        onFailure: () -> Unit = {},
        onSuccess: (List<ImageItemData>) -> Unit,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching { model.getImageList(page = currentPage) }
                .onSuccess {
                    withContext(Dispatchers.Main) {
                        view.hideLoading()
                        onSuccess(it)
                    }
                }
                .onFailure {
                    withContext(Dispatchers.Main) {
                        view.hideLoading()
                        onFailure()
                    }
                }
        }
    }

    private fun showLoading() {
        view.showLoading()
        view.hiedLoadingFailed()
    }
}
