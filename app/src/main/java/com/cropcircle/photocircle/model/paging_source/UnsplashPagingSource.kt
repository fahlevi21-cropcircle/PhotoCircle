package com.cropcircle.photocircle.model.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cropcircle.photocircle.model.PhotoItem
import com.cropcircle.photocircle.network.UnsplashApi
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_INDEX = 1

class UnsplashPagingSource(
    private val api: UnsplashApi,
    private val queries: MutableMap<String, String>
) : PagingSource<Int, PhotoItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoItem> {
        val position = params.key ?: STARTING_INDEX

        return try {
            //update Map values using put from MutableMap
            queries["per_page"] = params.loadSize.toString()
            queries["page"] = position.toString()
            val response = api.getLatestPhotos(queries)

            LoadResult.Page(
                data = response,
                prevKey = if (position == STARTING_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        }catch (exception:IOException){
            LoadResult.Error(exception)
        }catch (exception:HttpException){
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PhotoItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}