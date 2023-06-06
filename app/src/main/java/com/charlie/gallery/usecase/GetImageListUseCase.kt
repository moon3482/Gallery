package com.charlie.gallery.usecase

import com.charlie.gallery.db.GalleryDao
import com.charlie.gallery.local.ImageEntity
import kotlinx.coroutines.flow.Flow

class GetImageListUseCase(
    private val galleryDao: GalleryDao,
) {

    operator fun invoke(page: Int, limit: Int): Flow<List<ImageEntity>> {
        return galleryDao.getImages(limit, page)
    }
}