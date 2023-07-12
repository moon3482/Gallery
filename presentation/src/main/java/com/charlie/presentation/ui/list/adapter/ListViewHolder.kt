package com.charlie.presentation.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charlie.presentation.databinding.ItemGridImageBinding
import com.charlie.presentation.model.ListItemUiModel

class ListViewHolder(
    private val binding: ItemGridImageBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        listItemUiModel: ListItemUiModel,
        onClickViewHolder: ((Int) -> Unit)?,
    ) {
        with(binding) {
            url = listItemUiModel.downloadUrl

            imageviewItem.setOnClickListener {
                onClickViewHolder?.invoke(listItemUiModel.id)
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
