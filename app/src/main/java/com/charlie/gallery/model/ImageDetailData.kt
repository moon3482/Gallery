package com.charlie.gallery.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ImageDetailData(
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
)
