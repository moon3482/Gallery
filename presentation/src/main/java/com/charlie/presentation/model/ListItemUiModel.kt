package com.charlie.presentation.model

import com.charlie.domain.model.ImageModel

data class ListItemUiModel(
    val id: Int,
    val downloadUrl: String,
) {
    companion object {
        operator fun invoke(imageModel: ImageModel) = ListItemUiModel(
            id = imageModel.id,
            downloadUrl = imageModel.downloadUrl,
        )
    }
}
