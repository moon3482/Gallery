package com.charlie.gallery.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.charlie.gallery.local.ImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GalleryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ImageEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entityList: List<ImageEntity>)

    @Query("SELECT * FROM tb_image WHERE id = :id")
    suspend fun getImage(id: Int): ImageEntity?

    @Query("SELECT * FROM tb_image ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun getImages(limit: Int, offset: Int): Flow<List<ImageEntity>>

    @Update
    suspend fun update(entity: ImageEntity)

    @Query("UPDATE tb_image SET width = :width, height = :height ,download_url = :downloadUrl, url = :url WHERE id = :id")
    suspend fun update(id: Int, width: Int, height: Int, downloadUrl: String, url: String)

    @Delete
    suspend fun delete(entity: ImageEntity)

    @Query("DELETE FROM tb_image WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM tb_image")
    suspend fun deleteAll()

}
