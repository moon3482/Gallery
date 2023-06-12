package com.charlie.gallery.ui.detail

import com.charlie.gallery.local.GalleryDatabase
import com.charlie.gallery.model.ImageDetailModel
import com.charlie.gallery.remote.GalleryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailModel {
    private val local = GalleryDatabase.galleryDao
    private val remote = GalleryService

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