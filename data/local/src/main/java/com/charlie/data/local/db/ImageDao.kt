package com.charlie.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.charlie.data.local.model.ImageEntity

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(
        entity: ImageEntity,
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(
        entityList: List<ImageEntity>,
    )

    @Query("SELECT * FROM image WHERE id = :id")
    suspend fun get(
        id: Int,
    ): ImageEntity?

    @Query("SELECT * FROM image ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getList(
        limit: Int,
        offset: Int,
    ): List<ImageEntity>

    @Update
    suspend fun update(
        entity: ImageEntity,
    )

    @Delete
    suspend fun delete(
        entity: ImageEntity,
    )

    @Query("DELETE FROM image WHERE id = :id")
    suspend fun delete(
        id: Int,
    )

    @Query("DELETE FROM image")
    suspend fun deleteAll()
}
