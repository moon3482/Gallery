package com.charlie.remote.source

import com.charlie.remote.model.ImageResponse

interface ImageService {
    suspend fun requestImageList(page: Int, limit: Int): Result<List<ImageResponse>>
    suspend fun requestImageData(id: Int): Result<ImageResponse>
}
