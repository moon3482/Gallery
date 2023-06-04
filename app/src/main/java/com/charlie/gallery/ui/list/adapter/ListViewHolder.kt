package com.charlie.gallery.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charlie.gallery.databinding.ItemGridImageBinding
import com.charlie.gallery.model.ImageItemData

class ListViewHolder(
    private val binding: ItemGridImageBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        imageItemData: ImageItemData,
        onClickViewHolder: ((Int) -> Unit)?,
    ) {
        with(binding) {
            url = imageItemData.downloadUrl

            imageviewItem.setOnClickListener {
                onClickViewHolder?.invoke(imageItemData.id)
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
