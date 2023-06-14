package com.charlie.remote

import com.charlie.remote.model.ResponseData

interface GalleryService {
    suspend fun requestImageList(page: Int): Result<List<ResponseData>>
    suspend fun requestImageData(id: Int): Result<ResponseData>
}
