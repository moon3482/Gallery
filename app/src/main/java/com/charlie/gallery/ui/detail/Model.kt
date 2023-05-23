package com.charlie.gallery.ui.detail

import com.charlie.gallery.model.ImageDetailData

interface Model {
    suspend fun getImageDetailData(id: Int): ImageDetailData
}
