package com.charlie.gallery.ui.list

import com.charlie.gallery.network.RetrofitClient
import com.charlie.gallery.remote.ImageDetailDataResponse
import retrofit2.await

class ListModel {
    suspend operator fun invoke(page: Int): List<ImageDetailDataResponse> {
        return RetrofitClient
            .galleryApi
            .requestImageList(page = page)
            .await()
    }
}
