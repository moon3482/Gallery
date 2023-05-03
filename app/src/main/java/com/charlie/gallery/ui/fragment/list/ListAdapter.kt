package com.charlie.gallery.ui.fragment.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charlie.gallery.model.ImageData

class ListAdapter(
    private val imageDataList: MutableList<ImageData> = mutableListOf(),
    private val onClickViewHolder: (Int) -> Unit,
) : RecyclerView.Adapter<ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(parent)

    override fun getItemCount(): Int = imageDataList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = imageDataList[position]
        holder.bind(
            data,
            imageDataList[position].id,
            onClickViewHolder
        )
    }

    fun initList(imageDataList: List<ImageData>) {
        this.imageDataList.clear()
        this.imageDataList.addAll(imageDataList)
        notifyItemRangeChanged(0, this.imageDataList.size)
    }
}
