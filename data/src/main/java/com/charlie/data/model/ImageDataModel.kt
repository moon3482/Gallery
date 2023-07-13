package com.charlie.data.model

import com.charlie.data.local.model.ImageEntity
import com.charlie.data.remote.model.ImageResponse

data class ImageDataModel(
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
        operator fun invoke(
            imageEntity: ImageEntity,
        ) = ImageDataModel(
            id = imageEntity.id,
            author = imageEntity.author,
            width = imageEntity.width,
            height = imageEntity.height,
            downloadUrl = imageEntity.downloadUrl,
            url = imageEntity.url,
        )

        operator fun invoke(
            imageResponse: ImageResponse,
        ) = ImageDataModel(
            id = imageResponse.id,
            author = imageResponse.author,
            width = imageResponse.width,
            height = imageResponse.height,
            downloadUrl = imageResponse.downloadUrl,
            url = imageResponse.url,
        )
    }
}
