package com.cropcircle.photocircle.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.cropcircle.photocircle.model.PhotoItem
import com.cropcircle.photocircle.model.paging_source.SearchPhotosPagingSource
import com.cropcircle.photocircle.model.paging_source.UnsplashPagingSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class UnsplashRepository @Inject constructor(private val api: UnsplashApi) {
    fun getLatestPhotos(queries: MutableMap<String, String>) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = { UnsplashPagingSource(api, queries) }
        ).liveData

    fun searchPhotos(queries: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = { SearchPhotosPagingSource(api, queries) }
        ).liveData

    suspend fun getPhotoDetails(id: String): PhotoItem = api.photoDetails(id)
}