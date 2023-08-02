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
            val localData = local
                .getList(
                    offset = offset,
                    limit = limit,
                )
                .map { imageEntity -> ImageDataModel(imageEntity) }
            emit(localData)

            val remoteData = remote
                .getList(
                    page = page,
                    limit = limit,
                )
                .map { responseList ->
                    responseList.map { imageResponse ->
                        ImageDataModel(imageResponse)
                    }
                }
                .onSuccess { imageDataList ->
                    val entityList = imageDataList.map { imageDataModel -> imageDataModel.toEntity() }
                    local.insert(entityList)
                }
                .fold(
                    onSuccess = { it },
                    onFailure = { emptyList() },
                )
            emit(remoteData)
        }
    }

    override fun getImage(
        id: Int,
    ): Flow<ImageDataModel?> {
        return flow {
            val localData = local
                .get(id)
                ?.let { imageEntity -> ImageDataModel(imageEntity) }
            emit(localData)

            val remoteData = remote
                .get(id)
                .map { imageResponse -> ImageDataModel(imageResponse) }
                .onSuccess { imageData ->
                    val entity = imageData.toEntity()
                    local.insert(entity)
                }
                .fold(
                    onSuccess = { it },
                    onFailure = { null },
                )
            emit(remoteData)
        }
    }
}