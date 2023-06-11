package com.charlie.gallery.model

import com.charlie.gallery.local.model.ImageEntity
import com.charlie.gallery.remote.model.ImageDetailDataResponse

data class ImageDetailModel(
    val author: String,
    val width: Int,
    val height: Int,
    val downloadUrl: String,
    val url: String,
) {
    companion object {
        operator fun invoke(imageEntity: ImageEntity) = ImageDetailModel(
            author = imageEntity.author,
            width = imageEntity.width,
            height = imageEntity.height,
            downloadUrl = imageEntity.downloadUrl,
            url = imageEntity.url,
        )

        operator fun invoke(imageDetailDataResponse: ImageDetailDataResponse) = ImageDetailModel(
            author = imageDetailDataResponse.author,
            width = imageDetailDataResponse.width.toInt(),
            height = imageDetailDataResponse.height.toInt(),
            downloadUrl = imageDetailDataResponse.downloadUrl,
            url = imageDetailDataResponse.url,
        )
    }
}
