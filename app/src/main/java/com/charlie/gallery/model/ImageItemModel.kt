package com.charlie.gallery.model

import com.charlie.gallery.local.model.ImageEntity


data class ImageItemModel(
    val id: Int,
    val downloadUrl: String,
) {
    companion object {
        operator fun invoke(imageEntity: ImageEntity) = ImageItemModel(
            id = imageEntity.imageId,
            downloadUrl = imageEntity.downloadUrl,
        )
    }
}
