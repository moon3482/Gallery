package com.charlie.gallery.ui.fragment.list

import com.charlie.gallery.model.ImageItemData

interface ListContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showList(imageItemDataList: List<ImageItemData>)
        fun showDetailFragment(currentId: Int)
    }

    interface Presenter {
        fun start()
        fun onClickItem(currentId: Int)
    }

    interface Model {
        suspend fun getImageList(): List<ImageItemData>
    }
}
