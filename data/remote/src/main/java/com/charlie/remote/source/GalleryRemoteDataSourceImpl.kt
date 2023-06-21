package com.charlie.remote.source

import com.charlie.remote.api.GalleryApi
import com.charlie.remote.model.ImageResponse
import retrofit2.await
import javax.inject.Inject

class GalleryRemoteDataSourceImpl @Inject constructor(
    private val galleryApi: GalleryApi,
) : GalleryRemoteDataSource {

    override suspend fun requestImageList(page: Int, limit: Int): Result<List<ImageResponse>> {
        return runCatching { galleryApi.requestImageList(page = page, limit = limit).await() }
    }

    override suspend fun requestImageData(id: Int): Result<ImageResponse> {
        return runCatching { galleryApi.requestImage(id = id).await() }
    }
}
