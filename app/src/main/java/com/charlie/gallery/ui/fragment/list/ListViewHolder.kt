package com.charlie.gallery.ui.fragment.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.charlie.gallery.R
import com.charlie.gallery.databinding.ItemGridImageBinding
import com.charlie.gallery.model.ImageData

class ListViewHolder(
    private val binding: ItemGridImageBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        imageData: ImageData,
        currentImageId: Int,
        onClickViewHolder: (Int) -> Unit,
    ) {
        with(binding) {
            Glide.with(this.root)
                .load(imageData.downloadUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.loading)
                .error(R.drawable.close)
                .into(imageviewItem)

            imageviewItem.setOnClickListener {
                onClickViewHolder(currentImageId)
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