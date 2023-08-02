package com.charlie.presentation.ui.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charlie.presentation.model.ListItemUiModel

class ListAdapter(
    private val imageItemList: MutableList<ListItemUiModel> = mutableListOf(),
) : RecyclerView.Adapter<ListViewHolder>() {
    private var onClickItem: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListViewHolder = ListViewHolder(parent)

    override fun getItemCount(): Int = imageItemList.size

    override fun onBindViewHolder(
        holder: ListViewHolder,
        position: Int,
    ) {
        val data = imageItemList[position]
        holder.bind(
            listItemUiModel = data,
            onClickViewHolder = onClickItem
        )
    }

    fun updateList(
        imageItemModelList: List<ListItemUiModel>,
    ) {
        this.imageItemList.clear()
        this.imageItemList.addAll(imageItemModelList)
        notifyDataSetChanged()
    }

    fun setOnClickItem(
        onClick: (Int) -> Unit,
    ) {
        this.onClickItem = onClick
    }
}
