package com.charlie.remote.source

import com.charlie.remote.api.ImageApi
import com.charlie.remote.model.ImageResponse
import retrofit2.await
import javax.inject.Inject

class ImageServiceImpl @Inject constructor(
    private val imageApi: ImageApi,
) : ImageService {

    override suspend fun requestImageList(
        page: Int,
        limit: Int,
    ): Result<List<ImageResponse>> {
        return runCatching {
            imageApi
                .requestImageList(
                    page = page,
                    limit = limit,
                )
                .await()
        }
    }

    override suspend fun requestImageData(
        id: Int,
    ): Result<ImageResponse> {
        return runCatching {
            imageApi
                .requestImage(
                    id = id,
                )
                .await()
        }
    }
}