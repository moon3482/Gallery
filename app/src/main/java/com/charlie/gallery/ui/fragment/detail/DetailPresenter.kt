package com.charlie.gallery.ui.fragment.detail

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailPresenter(
    private val view: DetailContract.View,
    private val model: DetailContract.Model,
    private var currentId: Int = -1,
) : DetailContract.Presenter {

    override fun start() {
        if (currentId < 0) {
            view.exit()
            return
        }
        view.showLoading()
        setScreen()
    }

    override fun onClickUrl(url: String) {
        view.moveWebView(url = url)
    }

    override fun onClickPrevious() {
        if (currentId <= 0) {
            return
        }
        view.showLoading()
        currentId--
        setScreen()
    }

    override fun onClickNext() {
        if (currentId >= 29)
            return
        view.showLoading()
        currentId++
        setScreen()
    }

    private fun setScreen() {
        if (currentId <= 0) {
            view.hidePreviousButton()
        } else {
            view.showPreviousButton()
        }
        CoroutineScope(Dispatchers.IO).launch {

            runCatching { model.getImageDetailData(id = currentId) }
                .onSuccess {
                    withContext(Dispatchers.Main) {
                        view.hideLoading()
                        view.showSuccessDetail(imageDetailData = it)
                        view.setOnClickUrl(url = it.url)
                    }
                }
                .onFailure {
                    withContext(Dispatchers.Main) {
                        view.hideLoading()
                        view.showFailedDetail()
                    }
                }

            runCatching { model.getImageItemData(id = currentId) }
                .onSuccess {
                    withContext(Dispatchers.Main) {
                        view.showCurrentPreview(imageItemData = it)
                    }
                }
                .onFailure {
                    withContext(Dispatchers.Main) {
                        view.clearCurrentPreview()
                    }
                }

            runCatching { model.getImageItemData(id = currentId - 1) }
                .onSuccess {
                    withContext(Dispatchers.Main) {
                        view.showPreviousPreview(imageItemData = it)
                    }
                }
                .onFailure {
                    withContext(Dispatchers.Main) {
                        view.clearPreviousPreview()
                    }
                }

            runCatching { model.getImageItemData(id = currentId + 1) }
                .onSuccess {
                    withContext(Dispatchers.Main) {
                        view.showNextPreview(imageItemData = it)
                    }
                }
                .onFailure {
                    withContext(Dispatchers.Main) {
                        view.clearNextPreview()
                    }
                }
        }
    }
}
