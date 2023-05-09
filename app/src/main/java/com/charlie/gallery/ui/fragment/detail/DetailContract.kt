package com.charlie.gallery.ui.fragment.detail

import com.charlie.gallery.model.ImageDetailData
import com.charlie.gallery.model.ImageItemData

interface DetailContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showDetail(imageDetailData: ImageDetailData)
        fun showCurrentPreview(imageItemData: ImageItemData)
        fun showPreviousPreview(imageItemData: ImageItemData)
        fun clearPreviousPreview()
        fun showNextPreview(imageItemData: ImageItemData)
        fun clearNextPreview()
    }

    interface Presenter {
        fun start(id: Int)
        fun onClickPrevious()
        fun onClickNext()
    }

    interface Model {
        suspend fun getImageDetailData(id: Int): ImageDetailData
        suspend fun getImageItemData(id: Int): ImageItemData
        fun setCurrentId(id: Int)
        fun getCurrentId(): Int
    }
}
