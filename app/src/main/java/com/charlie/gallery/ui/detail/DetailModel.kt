package com.charlie.gallery.ui.detail

import com.charlie.gallery.model.ImageDetailData
import com.charlie.gallery.model.ImageItemData
import com.charlie.gallery.network.RetrofitClient
import retrofit2.await

class DetailModel : DetailContract.Model {

    override suspend fun getImageDetailData(id: Int): ImageDetailData {
        return RetrofitClient
            .galleryApi
            .requestImageDetail(id = id)
            .await()
    }

    override suspend fun getImageItemData(id: Int): ImageItemData {
        val imageDetailData = RetrofitClient
            .galleryApi
            .requestImageDetail(id = id)
            .await()
        return ImageItemData(imageDetailData = imageDetailData)
    }
}
