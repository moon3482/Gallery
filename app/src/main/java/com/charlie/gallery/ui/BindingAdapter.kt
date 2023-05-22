package com.charlie.gallery.ui

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.charlie.gallery.model.ImageItemData
import com.charlie.gallery.ui.list.UIEvent
import com.charlie.gallery.ui.list.adapter.ListAdapter

@BindingAdapter("bind:init")
fun initList(recyclerView: RecyclerView, imageItemDataList: List<ImageItemData>?) {
    (recyclerView.adapter as? ListAdapter)?.initList(imageItemDataList = imageItemDataList ?: emptyList())
}

@BindingAdapter("bind:nextPage")
fun nextPage(recyclerView: RecyclerView, imageItemDataList: List<ImageItemData>?) {
    (recyclerView.adapter as? ListAdapter)?.addList(imageItemDataList = imageItemDataList ?: emptyList())
}

@BindingAdapter("bind:isVisible")
fun isVisible(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}

@BindingAdapter("bind:itemClickListener")
fun RecyclerView.setItemClickListener(uiEvent: UIEvent) {
    (adapter as? ListAdapter)?.setOnClickItem(uiEvent::onClickItem)
}