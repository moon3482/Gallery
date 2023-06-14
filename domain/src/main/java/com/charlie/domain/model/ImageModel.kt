package com.charlie.domain.model

import com.charlie.data.model.ImageDataModel

data class ImageModel(
    val id: Int,
    val author: String,
    val width: Int,
    val height: Int,
    val downloadUrl: String,
    val url: String,
) {
    fun toDataModel() = ImageDataModel(
        id = id,
        author = author,
        width = width,
        height = height,
        downloadUrl = downloadUrl,
        url = url,
    )

    companion object {
        operator fun invoke(imageDataModel: ImageDataModel) = ImageModel(
            id = imageDataModel.id,
            author = imageDataModel.author,
            width = imageDataModel.width,
            height = imageDataModel.height,
            downloadUrl = imageDataModel.downloadUrl,
            url = imageDataModel.url,
        )
    }
}
