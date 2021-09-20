package com.cropcircle.photocircle.network

import androidx.lifecycle.LiveData
import com.cropcircle.photocircle.BuildConfig
import com.cropcircle.photocircle.model.PhotoItem
import com.cropcircle.photocircle.model.PhotosResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface UnsplashApi {

    companion object {
        const val API_KEY = BuildConfig.UNSPLASH_ACCESS_KEY
        const val BASE_URL = "https://api.unsplash.com/"
    }

    @Headers("Accept-Version: v1", "Authorization: Client-ID $API_KEY")
    @GET("photos")
    suspend fun getPhotos(
        @QueryMap queries: Map<String, String>
    ): List<PhotoItem>

    @Headers("Accept-Version: v1", "Authorization: Client-ID $API_KEY")
    @GET("search/photos")
    suspend fun searchPhoto(
        @QueryMap queries: Map<String, String>
    ): PhotosResponse

    @Headers("Accept-Version: v1", "Authorization: Client-ID $API_KEY")
    @GET("photos/{id}")
    suspend fun photoDetails(
        @Path("id") id: String
    ): PhotoItem
}