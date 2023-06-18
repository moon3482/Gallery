package com.charlie.local.source

import com.charlie.local.db.GalleryDao
import com.charlie.local.model.ImageEntity
import javax.inject.Inject

class GalleryLocalDataSourceImpl @Inject constructor(
    private val galleryDao: GalleryDao
) : GalleryLocalDataSource {
    override suspend fun insert(entity: ImageEntity) {
        galleryDao.insert(entity = entity)
    }

    override suspend fun insert(entityList: List<ImageEntity>) {
        galleryDao.insert(entityList = entityList)
    }

    override suspend fun get(id: Int): ImageEntity? {
        return galleryDao.get(id = id)
    }

    override suspend fun getList(offset: Int, limit: Int): List<ImageEntity> {
        return galleryDao.getList(limit = limit, offset = offset)
    }

    override suspend fun update(imageEntity: ImageEntity) {
        galleryDao.update(entity = imageEntity)
    }

    override suspend fun delete(id: Int) {
        galleryDao.delete(id = id)
    }

    override suspend fun deleteAll() {
        galleryDao.deleteAll()
    }
}