package com.cropcircle.photocircle.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.cropcircle.photocircle.network.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: UnsplashRepository,
    stateHandle: SavedStateHandle
) : ViewModel() {
    private val currentQueries = MutableLiveData(DEFAULT_QUERY)
    private val searchQuery = stateHandle.getLiveData(CURRENT_SEARCH_QUERY, DEFAULT_SEARCH_QUERY)

    val latestPhotos = currentQueries.switchMap { queries ->
        repository.getLatestPhotos(queries).cachedIn(viewModelScope)
    }

    val searchedPhotos = searchQuery.switchMap { q ->
        repository.searchPhotos(q).cachedIn(viewModelScope)
    }

    fun setQueries(queries: MutableMap<String, String>) {
        currentQueries.value = queries
    }

    fun search(query: String) {
        searchQuery.value = query
    }

    companion object {
        private val DEFAULT_QUERY = setDefaultQuery()
        private const val CURRENT_SEARCH_QUERY = "current_search"
        private const val DEFAULT_SEARCH_QUERY = "cat"

        //function to set default queries
        private fun setDefaultQuery(): MutableMap<String, String> {
            val queries = mutableMapOf<String, String>()
            queries["order_by"] = "popular"

            return queries
        }
    }
}