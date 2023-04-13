package com.charlie.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.charlie.gallery.databinding.ItemGridImageBinding
import com.charlie.gallery.model.ImageDataDto

class ListAdapter(
    private val onClickViewHolder: (ImageDataDto) -> Unit
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private val imageDataList = mutableListOf<ImageDataDto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = ItemGridImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageDataList.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = imageDataList[position]
        holder.itemView.post {
            val viewWidth = holder.itemView.width
            val viewHeight = holder.itemView.height
            holder.bind(data, viewWidth, viewHeight)
        }
        holder.itemView.setOnClickListener {
            onClickViewHolder(data)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    fun initList(imageDataDtoList: List<ImageDataDto>) {
        imageDataList.addAll(imageDataDtoList)
        notifyItemRangeChanged(0, imageDataList.size)
    }

    fun addList(imageDataDtoList: List<ImageDataDto>) {
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
        fun bind(imageDataDto: ImageDataDto, viewWidth: Int, viewHeight: Int) {

            Glide.with(this.itemView)
                .load(imageDataDto.downloadUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(viewWidth, viewHeight)
                .into(binding.root)

        }
    }
}
