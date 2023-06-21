package com.charlie.gallery.ui.detail

import com.charlie.gallery.local.GalleryDao
import com.charlie.gallery.model.ImageDetailModel
import com.charlie.gallery.remote.GalleryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailModel @Inject constructor(
    private val local: GalleryDao,
    private val remote: GalleryService,
) {
    fun get(id: Int): Flow<ImageDetailModel?> {
        return flow {
            local
                .get(id = id)
                ?.let {
                    emit(ImageDetailModel(it))
                }

            remote.requestImageDetail(id = id)
                .map { ImageDetailModel(it) }
                .onSuccess { local.insert(it.toEntity()) }
                .fold(
                    onSuccess = { emit(it) },
                    onFailure = { emit(null) },
                )
        }
    }
}
