package com.charlie.gallery.remote

import com.charlie.gallery.remote.model.ImageDetailDataResponse
import retrofit2.await
import javax.inject.Inject

class GalleryServiceImpl @Inject constructor(
    private val galleryApi: GalleryApi,
    private val limit: Int,
) : GalleryService {

    override suspend fun requestImageList(page: Int): Result<List<ImageDetailDataResponse>> {
        return runCatching { galleryApi.requestImageList(page = page, limit = limit).await() }
    }

    override suspend fun requestImageDetail(id: Int): Result<ImageDetailDataResponse> {
        return runCatching { galleryApi.requestImageDetail(id = id).await() }
    }
}
