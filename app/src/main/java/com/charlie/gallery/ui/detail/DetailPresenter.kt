package com.charlie.gallery.ui.detail

import com.charlie.gallery.model.ImageDetailData
import com.charlie.gallery.model.ImageItemData
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
        view.showLoading()
        currentId--
        setScreen()
    }

    override fun onClickNext() {
        view.showLoading()
        currentId++
        setScreen()
    }

    private fun setScreen() {
        if (currentId <= 0) {
            view.disablePreviousButton()
        } else {
            view.enablePreviousButton()
        }
        CoroutineScope(Dispatchers.IO).launch {

            loadPreview(
                currentId,
                onSuccess = {
                    view.hideLoading()
                    view.showDetail(imageDetailData = it)
                    view.showCurrentPreview(imageItemData = ImageItemData(it))
                },
                onFailure = {
                    view.hideLoading()
                    view.showDetail(imageDetailData = null)
                    view.showCurrentPreview(imageItemData = null)
                },
            )
            loadPreview(
                currentId + 1,
                onSuccess = {
                    view.showNextPreview(imageItemData = ImageItemData(it))
                },
                onFailure = {
                    view.showPreviousPreview(imageItemData = null)
                },
            )
            loadPreview(
                currentId - 1,
                onSuccess = {
                    view.enablePreviousButton()
                    view.showPreviousPreview(imageItemData = ImageItemData(it))
                },
                onFailure = {
                    view.disablePreviousButton()
                    view.showPreviousPreview(imageItemData = null)
                },
            )
        }
    }

    private suspend fun loadPreview(
        id: Int,
        onSuccess: (ImageDetailData) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        runCatching { model.getImageDetailData(id = id) }
            .fold(
                onSuccess = { withContext(Dispatchers.Main) { onSuccess(it) } },
                onFailure = { withContext(Dispatchers.Main) { onFailure(it) } },
            )
    }
}
