package com.cropcircle.photocircle.ui.detail

import androidx.lifecycle.*
import com.cropcircle.photocircle.model.PhotoItem
import com.cropcircle.photocircle.network.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: UnsplashRepository
) : ViewModel() {

    val details = MutableLiveData<PhotoItem>()
    suspend fun getDetails(id: String): PhotoItem = repository.getPhotoDetails(id)

    fun setData(item: PhotoItem){
        details.value = item
    }
}