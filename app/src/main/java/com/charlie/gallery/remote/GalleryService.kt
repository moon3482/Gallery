package com.charlie.gallery.remote

import com.charlie.gallery.remote.model.ImageDetailDataResponse

interface GalleryService {
    suspend fun requestImageList(page: Int): Result<List<ImageDetailDataResponse>>
    suspend fun requestImageDetail(id: Int): Result<ImageDetailDataResponse>
}
