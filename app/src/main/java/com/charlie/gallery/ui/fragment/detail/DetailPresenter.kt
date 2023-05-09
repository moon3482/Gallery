package com.charlie.gallery.ui.fragment.detail

import com.charlie.gallery.model.ImageItemData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailPresenter(
    private val view: DetailContract.View,
    private val model: DetailContract.Model,
) : DetailContract.Presenter {

    override fun start(id: Int) {
        view.showLoading()
        model.setCurrentId(id = id)
        setScreen(currentId = model.getCurrentId())
    }

    override fun onClickPrevious() {
        if (model.getCurrentId() <= 0)
            return
        view.showLoading()
        model.setCurrentId(id = model.getCurrentId() - 1)
        setScreen(currentId = model.getCurrentId())
    }

    override fun onClickNext() {
        if (model.getCurrentId() >= 29)
            return
        view.showLoading()
        model.setCurrentId(id = model.getCurrentId() + 1)
        setScreen(currentId = model.getCurrentId())
    }

    private fun setScreen(currentId: Int) {
        CoroutineScope(Dispatchers.IO).launch {

            val imageDetailData = runCatching {
                model.getImageDetailData(id = currentId)
            }
            imageDetailData.onSuccess {
                withContext(Dispatchers.Main) {
                    view.hideLoading()
                    view.showDetail(imageDetailData = it)
                }
            }

            getImageItem(id = currentId) {
                view.showCurrentPreview(imageItemData = it)
            }

            getImageItem(id = currentId - 1) {
                view.clearPreviousPreview()
                view.showPreviousPreview(imageItemData = it)
            }

            getImageItem(id = currentId + 1) {
                view.clearNextPreview()
                view.showNextPreview(imageItemData = it)
            }
        }
    }

    private suspend fun getImageItem(
        id: Int,
        onSuccess: (ImageItemData) -> Unit,
    ) {
        runCatching {
            model.getImageItemData(id = id)
        }.onSuccess {
            withContext(Dispatchers.Main) {
                onSuccess(it)
            }
        }
    }
}
