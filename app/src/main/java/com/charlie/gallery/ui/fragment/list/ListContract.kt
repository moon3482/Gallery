package com.charlie.gallery.ui.fragment.list

import com.charlie.gallery.model.ImageItemData

interface ListContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showList(imageItemDataList: List<ImageItemData>)
        fun showDetailFragment(currentId: Int)
        fun showFailedToast()
        fun showLoadingFailed()
        fun hiedLoadingFailed()
    }

    interface Presenter {
        fun start()
        fun onClickItem(currentId: Int)
        fun onClickReload()
    }

    interface Model {
        suspend fun getImageList(): List<ImageItemData>
    }
}
