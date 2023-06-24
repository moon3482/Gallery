package com.charlie.local.source

import com.charlie.local.db.ImageDao
import com.charlie.local.model.ImageEntity
import javax.inject.Inject

class GalleryLocalDataSourceImpl @Inject constructor(
    private val imageDao: ImageDao
) : GalleryLocalDataSource {
    override suspend fun insert(entity: ImageEntity) {
        imageDao.insert(entity = entity)
    }

    override suspend fun insert(entityList: List<ImageEntity>) {
        imageDao.insert(entityList = entityList)
    }

    override suspend fun get(id: Int): ImageEntity? {
        return imageDao.get(id = id)
    }

    override suspend fun getList(offset: Int, limit: Int): List<ImageEntity> {
        return imageDao.getList(limit = limit, offset = offset)
    }

    override suspend fun update(imageEntity: ImageEntity) {
        imageDao.update(entity = imageEntity)
    }

    override suspend fun delete(id: Int) {
        imageDao.delete(id = id)
    }

    override suspend fun deleteAll() {
        imageDao.deleteAll()
    }
}