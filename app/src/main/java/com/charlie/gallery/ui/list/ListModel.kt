package com.charlie.gallery.ui.list

import com.charlie.gallery.local.GalleryDao
import com.charlie.gallery.model.ImageItemModel
import com.charlie.gallery.remote.GalleryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListModel @Inject constructor(
    private val local: GalleryDao,
    private val remote: GalleryService,
    private val limit: Int,
) {

    fun getList(page: Int): Flow<List<ImageItemModel>> {
        return flow {
            val offset = (page - 1) * limit
            local
                .getList(offset = offset, limit = limit)
                .map { entity -> ImageItemModel(entity) }
                .let {
                    emit(it)
                }

            remote.requestImageList(page = page)
                .map {
                    it.map { entity -> com.charlie.gallery.model.ImageDetailModel(entity) }
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
