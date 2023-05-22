package com.charlie.gallery.ui.list

import com.charlie.gallery.model.ImageItemData

interface Model {
    suspend fun getImageList(page: Int): List<ImageItemData>
}
