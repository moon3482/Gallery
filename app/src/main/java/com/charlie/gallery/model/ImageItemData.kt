package com.charlie.gallery.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageItemData(
    @Json(name = "id")
    val id: Int,
    @Json(name = "download_url")
    val downloadUrl: String,
)