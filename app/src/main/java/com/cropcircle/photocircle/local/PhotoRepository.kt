package com.cropcircle.photocircle.local

import com.cropcircle.photocircle.model.PhotoItem
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val dao: PhotoDao
) {
    suspend fun insertPhotos(vararg photoItem: PhotoItem){
        return dao.insertPhotos(*photoItem)
    }
}