package com.charlie.gallery.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charlie.gallery.databinding.ItemGridImageBinding
import com.charlie.gallery.model.ImageItemModel

class ListViewHolder(
    private val binding: ItemGridImageBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        imageItemModel: ImageItemModel,
        onClickViewHolder: ((Int) -> Unit)?,
    ) {
        with(binding) {
            url = imageItemModel.downloadUrl

            imageviewItem.setOnClickListener {
                onClickViewHolder?.invoke(imageItemModel.id)
            }
        }
    }

    companion object {
        operator fun invoke(parent: ViewGroup) = ListViewHolder(
            ItemGridImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }
}
