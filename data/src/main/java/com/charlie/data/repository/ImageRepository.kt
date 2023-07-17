package com.charlie.data.repository

import com.charlie.data.model.ImageDataModel
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun getImageList(
        page: Int,
    ): Flow<List<ImageDataModel>>

    fun getImage(
        id: Int,
    ): Flow<ImageDataModel>
}
