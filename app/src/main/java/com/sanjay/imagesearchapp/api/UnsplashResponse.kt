package com.sanjay.imagesearchapp.api

import com.sanjay.imagesearchapp.data.UnsplashPhoto

data class UnsplashResponse(
    val results: List<UnsplashPhoto>
)
