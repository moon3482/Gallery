package com.charlie.remote.source

import com.charlie.remote.model.ImageResponse

interface GalleryRemoteDataSource {
    suspend fun requestImageList(page: Int, limit: Int): Result<List<ImageResponse>>
    suspend fun requestImageData(id: Int): Result<ImageResponse>
}
