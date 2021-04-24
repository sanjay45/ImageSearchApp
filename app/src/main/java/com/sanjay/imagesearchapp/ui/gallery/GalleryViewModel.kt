package com.sanjay.imagesearchapp.ui.gallery

import androidx.lifecycle.ViewModel
import com.sanjay.imagesearchapp.data.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val unsplashRepository: UnsplashRepository
    ): ViewModel() {


}