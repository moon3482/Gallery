package com.charlie.local.source

import com.charlie.local.model.ImageEntity

interface GalleryLocalDataSource {
    suspend fun insert(entity: ImageEntity)
    suspend fun insert(entityList: List<ImageEntity>)
    suspend fun get(id: Int): ImageEntity?
    suspend fun getList(offset: Int, limit: Int): List<ImageEntity>
    suspend fun update(imageEntity: ImageEntity)
    suspend fun delete(id: Int)
    suspend fun deleteAll()
}