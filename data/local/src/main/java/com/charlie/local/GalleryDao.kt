package com.charlie.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.charlie.local.model.ImageEntity

@Dao
interface GalleryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ImageEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entityList: List<ImageEntity>)

    @Query("SELECT * FROM tb_image WHERE id = :id")
    suspend fun get(id: Int): ImageEntity?

    @Query("SELECT * FROM tb_image ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getList(limit: Int, offset: Int): List<ImageEntity>

    @Update
    suspend fun update(entity: ImageEntity)

    @Delete
    suspend fun delete(entity: ImageEntity)

    @Query("DELETE FROM tb_image WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM tb_image")
    suspend fun deleteAll()

}
