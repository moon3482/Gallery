package com.charlie.gallery.usecase

import com.charlie.gallery.db.GalleryDao
import com.charlie.gallery.model.ImageDetailModel
import com.charlie.gallery.network.GalleryApi
import retrofit2.await

class GetDetailImageUseCase(
    private val galleryApi: GalleryApi,
    private val galleryDao: GalleryDao,
) {
    suspend operator fun invoke(id: Int): ImageDetailModel {
        return galleryDao.getImage(id)?.let {
            ImageDetailModel(it)
        } ?: run {
            galleryApi.requestImageDetail(id).await().let {
                ImageDetailModel(it)
            }
        }
    }
}
