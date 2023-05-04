package com.charlie.gallery.ui.fragment.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class ListDecoration : ItemDecoration() {

    private val span: Int = 2

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val itemCount: Int = state.itemCount
        val position: Int = parent.getChildAdapterPosition(view)

        if (position % span == 0) {
            outRect.right = 5
        } else {
            outRect.left = 5
        }

        if (position < span) {
            outRect.top = 0
            outRect.bottom = 4
        } else if (position >= itemCount - span) {
            outRect.bottom = 0
            outRect.top = 4
        } else {
            outRect.bottom = 4
            outRect.top = 4
        }
    }
}