package com.charlie.remote

import com.charlie.remote.model.ResponseData
import retrofit2.await
import javax.inject.Inject

class GalleryServiceImpl @Inject constructor(
    private val galleryApi: GalleryApi,
    private val limit: Int,
) : GalleryService {

    override suspend fun requestImageList(page: Int): Result<List<ResponseData>> {
        return runCatching { galleryApi.requestImageList(page = page, limit = limit).await() }
    }

    override suspend fun requestImageData(id: Int): Result<ResponseData> {
        return runCatching { galleryApi.requestImage(id = id).await() }
    }
}
