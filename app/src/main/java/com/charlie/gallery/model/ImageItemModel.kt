package com.charlie.gallery.model

import com.charlie.gallery.local.model.ImageEntity
import com.charlie.gallery.remote.model.ImageDetailDataResponse


data class ImageItemModel(
    val id: Int,
    val downloadUrl: String,
) {
    companion object {
        operator fun invoke(imageEntity: ImageEntity) = ImageItemModel(
            id = imageEntity.id,
            downloadUrl = imageEntity.downloadUrl,
        )

        operator fun invoke(imageDetailDataResponse: ImageDetailDataResponse) = ImageItemModel(
            id = imageDetailDataResponse.id,
            downloadUrl = imageDetailDataResponse.downloadUrl,
        )

        operator fun invoke(imageDetailModel: ImageDetailModel) = ImageItemModel(
            id = imageDetailModel.id,
            downloadUrl = imageDetailModel.downloadUrl,
        )
    }
}
