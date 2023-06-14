package com.charlie.data.repository

import com.charlie.data.model.ImageDataModel
import kotlinx.coroutines.flow.Flow

interface GalleryRepository {
    fun getImage(id: Int): Flow<ImageDataModel?>
    fun getImageList(page: Int): Flow<List<ImageDataModel>>
}
