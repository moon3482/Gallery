package com.charlie.gallery.ui.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charlie.gallery.model.ImageItemModel

class ListAdapter(
    private val imageItemModelList: MutableList<ImageItemModel> = mutableListOf(),
) : RecyclerView.Adapter<ListViewHolder>() {

    private var onClickItem: ((Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(parent)

    override fun getItemCount(): Int = imageItemModelList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = imageItemModelList[position]
        holder.bind(data, onClickItem)
    }

    fun updateList(imageItemModelList: List<ImageItemModel>) {
        this.imageItemModelList.clear()
        this.imageItemModelList.addAll(imageItemModelList)
        notifyDataSetChanged()
    }

    fun setOnClickItem(onClick: (Int) -> Unit) {
        this.onClickItem = onClick
    }
}
