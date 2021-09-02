package com.cropcircle.photocircle.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cropcircle.photocircle.model.PhotoItem

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photo")
    fun getAll(): LiveData<List<PhotoItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(vararg photoItem: PhotoItem)
}