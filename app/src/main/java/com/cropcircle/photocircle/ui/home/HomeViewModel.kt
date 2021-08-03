package com.cropcircle.photocircle.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.cropcircle.photocircle.network.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: UnsplashRepository
) : ViewModel() {
    private val currentQueries = MutableLiveData(DEFAULT_QUERY)

    val latestPhotos = currentQueries.switchMap { queries->
        repository.getLatestPhotos(queries).cachedIn(viewModelScope)
    }

    fun setQueries(queries: MutableMap<String,String>){
        currentQueries.value = queries
    }

    companion object {
        private val DEFAULT_QUERY = setDefaultQuery()

        //function to set default queries
        private fun setDefaultQuery(): MutableMap<String, String> {
            val queries = mutableMapOf<String, String>()
            queries["order_by"] = "popular"

            return queries
        }
    }
}