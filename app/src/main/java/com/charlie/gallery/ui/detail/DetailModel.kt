package com.charlie.gallery.ui.detail

import com.charlie.gallery.model.ImageDetailData
import com.charlie.gallery.network.RetrofitClient
import retrofit2.await

class DetailModel {

    suspend operator fun invoke(id: Int): ImageDetailData {
        return RetrofitClient
            .galleryApi
            .requestImageDetail(id = id)
            .await()
    }
}
