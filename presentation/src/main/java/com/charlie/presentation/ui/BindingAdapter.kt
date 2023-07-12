package com.charlie.presentation.ui

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charlie.presentation.R
import com.charlie.presentation.model.ListItemUiModel
import com.charlie.presentation.ui.detail.DetailUIEvent
import com.charlie.presentation.ui.list.ListUIEvent
import com.charlie.presentation.ui.list.adapter.ListAdapter
import com.charlie.presentation.widget.LabelWidget

@BindingAdapter("bind:list")
fun RecyclerView.setList(imageItemModelList: List<ListItemUiModel>?) {
    (this.adapter as? ListAdapter)?.updateList(
        imageItemModelList = imageItemModelList ?: emptyList()
    )
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

@BindingAdapter("bind:onClickUrl")
fun LabelWidget.onClickUrl(event: DetailUIEvent?) {
    this.setOnClickListener {
        event?.onClickUrl(this.urlLink)
    }
}
