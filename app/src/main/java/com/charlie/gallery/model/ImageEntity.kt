package com.charlie.gallery.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_image")
data class ImageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("row_id")
    private val id: Int = 0,
    @ColumnInfo(name = "id")
    val imageId: Int,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "width")
    val width: Int,
    @ColumnInfo(name = "height")
    val height: Int,
    @ColumnInfo(name = "download_url")
    val downloadUrl: String,
    @ColumnInfo(name = "url")
    val url: String,
)
