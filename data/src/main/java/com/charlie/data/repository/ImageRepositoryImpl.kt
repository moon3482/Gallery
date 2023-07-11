package com.charlie.data.repository

import com.charlie.data.local.db.ImageDao
import com.charlie.data.model.ImageDataModel
import com.charlie.data.remote.source.ImageService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val local: ImageDao,
    private val remote: ImageService,
    private val limit: Int,
) : ImageRepository {
    override fun getImage(id: Int): Flow<ImageDataModel?> {
        return flow {
            emit(
                local
                    .get(id = id)
                    ?.let { ImageDataModel(it) }
                    ?: ImageDataModel()
            )

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