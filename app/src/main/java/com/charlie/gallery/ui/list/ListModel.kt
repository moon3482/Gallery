package com.charlie.gallery.ui.list

import com.charlie.gallery.local.GalleryDatabase
import com.charlie.gallery.model.ImageDetailModel
import com.charlie.gallery.model.ImageItemModel
import com.charlie.gallery.remote.GalleryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ListModel {
    private val local = GalleryDatabase.galleryDao
    private val remote = GalleryService
    fun getList(page: Int): Flow<List<ImageItemModel>> {
        return flow {
            val offset = (page - 1) * GalleryService.LIMIT
            local
                .getList(offset = offset, limit = GalleryService.LIMIT)
                .map { entity -> ImageItemModel(entity) }
                .let {
                    emit(it)
                }

            remote.requestImageList(page = page)
                .map {
                    it.map { entity -> ImageDetailModel(entity) }
                }
                .onSuccess {
                    local.insert(it.map { model -> model.toEntity() })
                }
                .map {
                    it.map { model -> ImageItemModel(model) }
                }
                .fold(
                    onSuccess = { it },
                    onFailure = { emptyList() },
                ).let {
                    emit(it)
                }
        }
    }
}
