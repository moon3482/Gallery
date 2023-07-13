package com.charlie.presentation.model

import com.charlie.domain.model.ImageModel

data class DetailUiModel(
    val id: Int,
    val author: String,
    val width: Int,
    val height: Int,
    val downloadUrl: String,
    val url: String,
) {
    companion object {
        operator fun invoke(
            imageModel: ImageModel,
        ) = DetailUiModel(
            id = imageModel.id,
            author = imageModel.author,
            width = imageModel.width,
            height = imageModel.height,
            downloadUrl = imageModel.downloadUrl,
            url = imageModel.url,
        )
    }
}
