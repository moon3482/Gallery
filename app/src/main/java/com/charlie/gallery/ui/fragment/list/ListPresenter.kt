package com.charlie.gallery.ui.fragment.list

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListPresenter(
    private val view: ListContract.View,
    private val model: ListContract.Model,
) : ListContract.Presenter {
    override fun start() {
        view.showLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val imageItemDataList = runCatching { model.getImageList() }
            imageItemDataList.onSuccess {
                withContext(Dispatchers.Main) {
                    view.hideLoading()
                    view.showList(imageItemDataList = it)
                }
            }
        }
    }

    override fun onClickItem(currentId: Int) {
        view.showDetailFragment(currentId = currentId)
    }
}
