package com.charlie.gallery.ui.detail

import com.charlie.gallery.model.ImageDetailData
import com.charlie.gallery.model.ImageItemData

interface DetailContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showSuccessDetail(imageDetailData: ImageDetailData?)
        fun showFailedDetail()
        fun showCurrentPreview(imageItemData: ImageItemData)
        fun clearCurrentPreview()
        fun showPreviousPreview(imageItemData: ImageItemData)
        fun clearPreviousPreview()
        fun showNextPreview(imageItemData: ImageItemData)
        fun clearNextPreview()
        fun enablePreviousButton()
        fun disablePreviousButton()
        fun setOnClickUrl(url: String)
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
        suspend fun getImageItemData(id: Int): ImageItemData
    }
}
