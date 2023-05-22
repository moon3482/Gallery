package com.charlie.gallery.ui.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charlie.gallery.model.ImageItemData

class ListAdapter(
    private val imageItemDataList: MutableList<ImageItemData> = mutableListOf(),
) : RecyclerView.Adapter<ListViewHolder>() {

    private var onClickItem: ((Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(parent)

    override fun getItemCount(): Int = imageItemDataList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = imageItemDataList[position]
        holder.bind(data, onClickItem)
    }

    fun initList(imageItemDataList: List<ImageItemData>) {
        this.imageItemDataList.clear()
        this.imageItemDataList.addAll(imageItemDataList)
        notifyItemRangeChanged(0, this.imageItemDataList.size)
    }

    fun addList(imageItemDataList: List<ImageItemData>) {
        val temp = this.imageItemDataList.size
        this.imageItemDataList.addAll(imageItemDataList)
        notifyItemRangeInserted(temp, imageItemDataList.size)
    }

    fun setOnClickItem(onClick: (Int) -> Unit) {
        this.onClickItem = onClick
    }
}
