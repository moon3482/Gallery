package com.charlie.gallery.ui.detail

import com.charlie.gallery.model.ImageDetailData
import com.charlie.gallery.model.ImageItemData

interface DetailContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showDetail(imageDetailData: ImageDetailData?)
        fun showCurrentPreview(imageItemData: ImageItemData?)
        fun showPreviousPreview(imageItemData: ImageItemData?)
        fun showNextPreview(imageItemData: ImageItemData?)
        fun enablePreviousButton()
        fun disablePreviousButton()
        fun moveWebView(url: String)
        fun exit()
    }

    interface Presenter {
        fun start()
        fun onClickUrl(url: String)
        fun onClickPrevious()
        fun onClickNext()
    }

    interface Model {
        suspend fun getImageDetailData(id: Int): ImageDetailData
    }
}
