package com.charlie.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageResponse(
    val id: Int,
    val author: String,
    val width: Long,
    val height: Long,
    val url: String,
    @Json(name = "download_url")
    val downloadUrl: String,
)
