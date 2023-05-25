package com.charlie.gallery.ui.list

import com.charlie.gallery.model.ImageItemData
import com.charlie.gallery.network.RetrofitClient
import retrofit2.await

class ListModel : Model {
    override suspend fun getImageList(page: Int): List<ImageItemData> {
        return RetrofitClient
            .galleryApi
            .requestImageList(page = page)
            .await()
    }
}
