package com.charlie.gallery.ui.list

import com.charlie.gallery.model.ImageItemData
import com.charlie.gallery.network.RetrofitClient
import retrofit2.await

class ListModel : ListContract.Model {
    override suspend fun getImageList(): List<ImageItemData> {
        return RetrofitClient
            .galleryApi
            .requestImageList()
            .await()
    }
}
