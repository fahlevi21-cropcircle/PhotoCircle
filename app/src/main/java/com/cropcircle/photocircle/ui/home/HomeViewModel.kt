package com.cropcircle.photocircle.ui.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.cropcircle.photocircle.model.PhotoItem
import com.cropcircle.photocircle.network.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UnsplashRepository,
    stateHandle: SavedStateHandle
) : ViewModel() {

    init {
        loadLatestPhotos()
        loadPopularPhotos()
    }

    private val currentQueries = MutableLiveData(DEFAULT_QUERY)
    private val searchQuery = stateHandle.getLiveData(CURRENT_SEARCH_QUERY, DEFAULT_SEARCH_QUERY)
    private val _latestPhotos = MutableLiveData<List<PhotoItem>>()
    private val _popularPhotos = MutableLiveData<List<PhotoItem>>()

    val latestPhotos get() = _latestPhotos
    val popularPhotos get() = _popularPhotos

    private fun loadLatestPhotos() = viewModelScope.launch {
        repository.fetchLatestPhoto()
            .collect {
                _latestPhotos.postValue(it)
            }
    }

    private fun loadPopularPhotos() = viewModelScope.launch {
        repository.fetchPopularPhoto()
            .collect {
                _popularPhotos.postValue(it)
            }
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