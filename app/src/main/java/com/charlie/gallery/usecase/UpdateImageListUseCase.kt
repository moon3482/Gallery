package com.charlie.gallery.usecase

import com.charlie.gallery.db.GalleryDao
import com.charlie.gallery.local.ImageEntity
import com.charlie.gallery.ui.list.ListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateImageListUseCase(
    private val getImageList: ListModel,
    private val galleryDao: GalleryDao,
) {

    suspend operator fun invoke(
        page: Int,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        runCatching { getImageList(page) }
            .onSuccess { imageResponseList ->
                imageResponseList
                    .map { ImageEntity(it) }
                    .let { galleryDao.insert(it) }
                withContext(Dispatchers.Main) {
                    onSuccess()
                }
            }
            .onFailure {
                withContext(Dispatchers.Main) {
                    onFailure(it)
                }
            }
    }
}