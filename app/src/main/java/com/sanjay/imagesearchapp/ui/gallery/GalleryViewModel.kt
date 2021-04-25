package com.sanjay.imagesearchapp.ui.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sanjay.imagesearchapp.data.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val unsplashRepository: UnsplashRepository
    ): ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

        val photos = currentQuery.switchMap {
            unsplashRepository.getSearchResults(it).cachedIn(viewModelScope)
            //cachedIn(viewModelScope) to cache this livedata otherwise we will get the
            //crash when we rotated the device because we cannot load from the same paging
            //data twice.
        }


    fun searchPhotos(query:String) {
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = "cats"
    }


}