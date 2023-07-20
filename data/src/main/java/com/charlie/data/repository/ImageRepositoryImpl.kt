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
    override fun getImageList(
        page: Int,
    ): Flow<List<ImageDataModel>> {
        return flow {
            val offset = (page - 1) * limit
            local
                .getList(limit, offset)
                .map { ImageDataModel(it) }
                .let { emit(it) }

            remote
                .getList(page, limit)
                .map { responseList ->
                    responseList.map { ImageDataModel(it) }
                }
                .onSuccess { imageDataList ->
                    imageDataList
                        .map { it.toEntity() }
                        .also { local.insert(it) }
                }
                .fold(
                    onSuccess = { emit(it) },
                    onFailure = { emit(emptyList()) },
                )
        }
    }

    override fun getImage(
        id: Int,
    ): Flow<ImageDataModel?> {
        return flow {
            local
                .get(id)
                ?.let { ImageDataModel(it) }
                ?.run { emit(this) }

            remote
                .get(id)
                .map { ImageDataModel(it) }
                .onSuccess { imageData ->
                    imageData
                        .toEntity()
                        .also { local.insert(it) }
                }
                .fold(
                    onSuccess = { emit(it) },
                    onFailure = { emit(null) },
                )

        }
    }
}