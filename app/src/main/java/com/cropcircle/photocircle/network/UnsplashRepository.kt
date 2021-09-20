package com.cropcircle.photocircle.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.cropcircle.photocircle.model.PhotoItem
import com.cropcircle.photocircle.model.paging_source.SearchPhotosPagingSource
import com.cropcircle.photocircle.model.paging_source.UnsplashPagingSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.random.Random

@ActivityRetainedScoped
class UnsplashRepository @Inject constructor(private val api: UnsplashApi) {
    fun getLatestPhotos() =
        Pager(
            config = PagingConfig(
                pageSize = 70,
                enablePlaceholders = false,
                initialLoadSize = 5
            ),
            pagingSourceFactory = { UnsplashPagingSource(api) }
        ).liveData

    fun fetchLatestPhoto(): Flow<List<PhotoItem>>{
        val map = mutableMapOf<String, String>()
        val rand = Random
        val page = rand.nextInt(1, 15)
        map["page"] = page.toString()
        map["per_page"] = "15"
        map["order_by"] = "latest"
        return flow {
            val list = api.getPhotos(map)
                .map {
                    it
                }
            emit(list)
        }.flowOn(Dispatchers.IO)
    }

    fun fetchPopularPhoto(): Flow<List<PhotoItem>>{
        val map = mutableMapOf<String, String>()
        val rand = Random
        val page = rand.nextInt(1, 15)
        map["page"] = page.toString()
        map["per_page"] = "10"
        map["order_by"] = "popular"
        return flow {
            val list = api.getPhotos(map)
                .map {
                    it
                }
            emit(list)
        }.flowOn(Dispatchers.IO)
    }

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