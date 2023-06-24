package com.charlie.data.repository

import com.charlie.data.model.ImageDataModel
import com.charlie.local.db.ImageDao
import com.charlie.remote.source.GalleryRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val local: ImageDao,
    private val remote: GalleryRemoteDataSource,
    private val limit: Int,
) : GalleryRepository {
    override fun getImage(id: Int): Flow<ImageDataModel?> {
        return flow {
            local.get(id = id)?.let {
                emit(ImageDataModel(it))
            }
            remote.requestImageData(id = id)
                .map { ImageDataModel(it) }
                .onSuccess { imageDataModel -> local.insert(imageDataModel.toEntity()) }
                .fold(
                    onSuccess = { emit(it) },
                    onFailure = { emit(null) },
                )
        }
    }

    override fun getImageList(page: Int): Flow<List<ImageDataModel>> {
        return flow {
            val offset = (page - 1) * limit
            local
                .getList(offset = offset, limit = limit)
                .map { entity -> ImageDataModel(entity) }
                .let {
                    emit(it)
                }

            remote.requestImageList(page = page, limit = limit)
                .map {
                    it.map { entity -> ImageDataModel(entity) }
                }
                .onSuccess {
                    local.insert(it.map { model -> model.toEntity() })
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