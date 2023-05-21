package com.charlie.gallery.ui.detail

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
            loadPreview(
                currentId,
                onSuccess = {
                    view.showCurrentPreview(imageItemData = it)
                },
                onFailure = {
                    view.clearCurrentPreview()
                },
            )
            loadPreview(
                currentId + 1,
                onSuccess = {
                    view.showNextPreview(imageItemData = it)
                },
                onFailure = {
                    view.clearNextPreview()
                },
            )
            loadPreview(
                currentId - 1,
                onSuccess = {
                    view.enablePreviousButton()
                    view.showPreviousPreview(imageItemData = it)
                },
                onFailure = {
                    view.disablePreviousButton()
                    view.clearPreviousPreview()
                },
            )
        }
    }

    private suspend fun loadPreview(
        id: Int,
        onSuccess: (ImageItemData) -> Unit,
        onFailure: () -> Unit,
    ) {
        runCatching { model.getImageItemData(id = id) }
            .onSuccess {
                withContext(Dispatchers.Main) {
                    onSuccess(it)

                }
            }
            .onFailure {
                withContext(Dispatchers.Main) {
                    onFailure()
                }
            }
    }
}
