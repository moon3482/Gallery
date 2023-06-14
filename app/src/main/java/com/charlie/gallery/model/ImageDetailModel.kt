package com.charlie.gallery.model

import com.charlie.gallery.local.model.ImageEntity
import com.charlie.gallery.remote.model.ImageDetailDataResponse

data class ImageDetailModel(
    val id: Int,
    val author: String,
    val width: Int,
    val height: Int,
    val downloadUrl: String,
    val url: String,
) {
    fun toEntity() = ImageEntity(
        id = id,
        author = author,
        width = width,
        height = height,
        downloadUrl = downloadUrl,
        url = url,
    )

    companion object {
        operator fun invoke(imageEntity: ImageEntity) = ImageDetailModel(
            id = imageEntity.id,
            author = imageEntity.author,
            width = imageEntity.width,
            height = imageEntity.height,
            downloadUrl = imageEntity.downloadUrl,
            url = imageEntity.url,
        )

        operator fun invoke(imageDetailDataResponse: ImageDetailDataResponse) = ImageDetailModel(
            id = imageDetailDataResponse.id,
            author = imageDetailDataResponse.author,
            width = imageDetailDataResponse.width.toInt(),
            height = imageDetailDataResponse.height.toInt(),
            downloadUrl = imageDetailDataResponse.downloadUrl,
            url = imageDetailDataResponse.url,
        )
    }
}
