package com.charlie.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.charlie.gallery.databinding.ItemGridImageBinding
import com.charlie.gallery.model.ImageData

class ListAdapter(
    private val onClickViewHolder: (Int?, Int, Int?) -> Unit
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private val imageDataList = mutableListOf<ImageData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = ItemGridImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(view)
    }

    override fun getItemCount() = imageDataList.size


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = imageDataList[position]
        holder.bind(data)
        holder.itemView.setOnClickListener {
            onClickViewHolder(
                imageDataList.getOrNull(position - 1)?.id,
                imageDataList[position].id,
                imageDataList.getOrNull(position + 1)?.id,
            )
        }
    }

    fun initList(imageDataList: List<ImageData>) {
        this.imageDataList.clear()
        this.imageDataList.addAll(imageDataList)
        notifyItemRangeChanged(0, this.imageDataList.size)
    }

    inner class ListViewHolder(
        private val binding: ItemGridImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageData: ImageData) {
            Glide.with(this.itemView)
                .load(imageData.downloadUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.loading)
                .error(R.drawable.close)
                .into(binding.root)
        }
    }
}
