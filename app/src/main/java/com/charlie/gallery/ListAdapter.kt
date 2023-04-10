package com.charlie.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charlie.gallery.databinding.ItemGridImageBinding
import com.charlie.gallery.model.ImageData

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private val imageDataList = mutableListOf<ImageData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = ItemGridImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageDataList.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = imageDataList[position]
        holder.bind(data)
    }

    fun initList(imageDataDtoList: List<ImageData>) {
        imageDataList.addAll(imageDataDtoList)
        notifyItemRangeChanged(0, imageDataList.size)
    }

    fun addList(imageDataDtoList: List<ImageData>) {
        val tempSize = imageDataList.lastIndex
        imageDataList.addAll(imageDataDtoList)
        notifyItemRangeChanged(tempSize, imageDataList.size)
    }

    fun clearList() {
        imageDataList.clear()
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val binding: ItemGridImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageData: ImageData) {
            Glide.with(this.itemView)
                .load(imageData.downLoadUrl)
                .into(binding.root)

        }
    }
}
