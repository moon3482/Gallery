package com.charlie.gallery.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.charlie.gallery.remote.model.ImageDetailDataResponse

@Entity(tableName = "tb_image", indices = [Index(value = ["id"], unique = true)])
data class ImageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("row_id")
    val id: Int = 0,
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
) {
    companion object {
        operator fun invoke(imageDetailDataResponse: ImageDetailDataResponse) = ImageEntity(
            imageId = imageDetailDataResponse.id,
            author = imageDetailDataResponse.author,
            width = imageDetailDataResponse.width.toInt(),
            height = imageDetailDataResponse.height.toInt(),
            downloadUrl = imageDetailDataResponse.downloadUrl,
            url = imageDetailDataResponse.url,
        )
    }
}
