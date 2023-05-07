package com.charlie.gallery.ui.fragment.list

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class ListDecoration(
    @Px private val width: Int,
    @Px private val height: Int,
) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val spanCount = when (parent.layoutManager) {
            is GridLayoutManager -> (parent.layoutManager as GridLayoutManager).spanCount
            else -> 1
        }
        val spanIndex = when (parent.layoutManager) {
            is GridLayoutManager -> (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex
            else -> 1
        }
        val itemCount = state.itemCount
        val position: Int = parent.getChildAdapterPosition(view)

        when (spanIndex) {
            0 -> outRect.right = width / 2

            spanCount - 1 -> outRect.left = width / 2

            else -> {
                outRect.left = width / 2
                outRect.right = width / 2
            }
        }

        when {
            position < spanCount -> outRect.bottom = height / 2
            
            (itemCount - spanCount) <= position -> outRect.top = height / 2

            else -> {
                outRect.bottom = height / 2
                outRect.top = height / 2
            }
        }
    }
}