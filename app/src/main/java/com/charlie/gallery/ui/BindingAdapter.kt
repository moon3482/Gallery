package com.charlie.gallery.ui

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charlie.gallery.R
import com.charlie.gallery.model.ImageItemData
import com.charlie.gallery.ui.detail.DetailUIEvent
import com.charlie.gallery.ui.list.ListUIEvent
import com.charlie.gallery.ui.list.adapter.ListAdapter

@BindingAdapter("bind:init")
fun RecyclerView.initList(imageItemDataList: List<ImageItemData>?) {
    (this.adapter as? ListAdapter)?.initList(imageItemDataList = imageItemDataList ?: emptyList())
}

@BindingAdapter("bind:nextPage")
fun RecyclerView.nextPage(imageItemDataList: List<ImageItemData>?) {
    (this.adapter as? ListAdapter)?.addList(imageItemDataList = imageItemDataList ?: emptyList())
}

@BindingAdapter("bind:isVisible")
fun View.isVisible(isVisible: Boolean) {
    this.isVisible = isVisible
}

@BindingAdapter("bind:itemClickListener")
fun RecyclerView.setItemClickListener(listUiEvent: ListUIEvent) {
    (adapter as? ListAdapter)?.setOnClickItem(listUiEvent::onClickItem)
}

@BindingAdapter("bind:detailImage")
fun ImageView.setDetailImage(imageUrl: String?) {
    Glide.with(this)
        .load(imageUrl)
        .placeholder(R.drawable.loading)
        .error(if (imageUrl != null) R.drawable.close else null)
        .into(this)
}

@BindingAdapter("bind:previewImage")
fun ImageView.setPreviewImage(imageUrl: String?) {
    Glide.with(this)
        .load(imageUrl)
        .into(this)
}
