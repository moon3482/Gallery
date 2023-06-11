package com.charlie.gallery.remote

import com.charlie.gallery.remote.model.ImageDetailDataResponse
import retrofit2.await

object GalleryService {

    const val LIMIT = 30
    private val api = RetrofitClient.galleryApi

    suspend fun requestImageList(page: Int): Result<List<ImageDetailDataResponse>> {
        return runCatching { api.requestImageList(page = page, limit = LIMIT).await() }
    }

    suspend fun requestImageDetail(id: Int): Result<ImageDetailDataResponse> {
        return runCatching { api.requestImageDetail(id = id).await() }
    }
}
