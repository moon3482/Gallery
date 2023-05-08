package com.charlie.gallery.ui.fragment.detail

import com.charlie.gallery.model.ImageDetailData
import com.charlie.gallery.model.ImageItemData
import com.charlie.gallery.network.RetrofitClient
import retrofit2.await

class DetailModel : DetailContract.Model {

    private var currentId: Int = 0

    override suspend fun getImageDetailData(id: Int): ImageDetailData {
        return RetrofitClient
            .galleryApi
            .requestImageDetail(id = id)
            .await()
    }

    override suspend fun getImageItemData(id: Int): ImageItemData {
        return RetrofitClient
            .galleryApi
            .requestImageItem(id = id)
            .await()
    }

    override fun setCurrentId(id: Int) {
        currentId = id
    }

    override fun getCurrentId() = currentId
}
