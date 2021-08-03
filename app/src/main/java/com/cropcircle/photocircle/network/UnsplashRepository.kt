package com.cropcircle.photocircle.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.cropcircle.photocircle.model.PhotoItem
import com.cropcircle.photocircle.model.paging_source.UnsplashPagingSource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UnsplashRepository @Inject constructor(private val api: UnsplashApi) {
    fun getLatestPhotos(queries: MutableMap<String, String>) =
        Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashPagingSource(api, queries) }
        ).liveData

    fun searchPhotos(queries: MutableMap<String, String>) =
        Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashPagingSource(api, queries) }
        ).liveData

    suspend fun getPhotoDetails(id: String): PhotoItem = api.photoDetails(id)
}