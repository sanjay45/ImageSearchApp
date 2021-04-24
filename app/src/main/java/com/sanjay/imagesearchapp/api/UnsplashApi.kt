package com.sanjay.imagesearchapp.api


import com.sanjay.imagesearchapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        const val API_KEY = BuildConfig.UNSPLASH_ACCESS_KEY
    }

    /**Headers are basically metadata about the request and we have to send two of them*/
    @Headers("Accept-Version: v1", "Authorization: Client-ID $API_KEY")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page")  page:Int,
        @Query("per_page") perPage:Int
    ) : UnsplashResponse
}