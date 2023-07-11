package com.charlie.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image")
data class ImageEntity(
    @PrimaryKey
    val id: Int,
    val author: String,
    val width: Int,
    val height: Int,
    @ColumnInfo(name = "download_url")
    val downloadUrl: String,
    val url: String,
)
