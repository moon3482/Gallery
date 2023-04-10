package com.charlie.gallery.model

import com.squareup.moshi.Json

data class ImageDataDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "author")
    val author: String,
    @Json(name = "width")
    val width: Long,
    @Json(name = "height")
    val height: Long,
    @Json(name = "url")
    val url: String,
    @Json(name = "download_url")
    val downloadUrl: String,
) {
    fun toImage(): ImageData {
        return ImageData(
            id = id,
            downLoadUrl = downloadUrl,
        )
    }
}
