package com.charlie.data.remote.source

import com.charlie.data.remote.model.ImageResponse

interface ImageService {
    suspend fun getList(
        page: Int,
        limit: Int,
    ): Result<List<ImageResponse>>

    suspend fun get(
        id: Int,
    ): Result<ImageResponse>
}
