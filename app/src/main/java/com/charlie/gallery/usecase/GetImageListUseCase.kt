package com.charlie.gallery.usecase

import com.charlie.gallery.local.GalleryDao
import com.charlie.gallery.local.model.ImageEntity
import kotlinx.coroutines.flow.Flow

class GetImageListUseCase(
    private val galleryDao: GalleryDao,
) {

    operator fun invoke(page: Int, limit: Int): Flow<List<ImageEntity>> {
        return galleryDao.getImages(limit, page)
    }
}