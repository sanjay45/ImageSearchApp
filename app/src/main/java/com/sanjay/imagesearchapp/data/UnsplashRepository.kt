package com.sanjay.imagesearchapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.sanjay.imagesearchapp.api.UnsplashApi
import javax.inject.Inject

class UnsplashRepository @Inject constructor(private val unsplashApi: UnsplashApi) {

    fun getSearchResults(query: String) =
        //Pager will use our PagingSource to create PagingData
        Pager(
            config = PagingConfig(
                //this pageSize will pass to the PagingSource  params.loadSize value to our
                //searchPhotos() method.
                pageSize = 20,
                //With maxSize value we define at what number of items in our recyclerview
                //we actually want to start dropping items because otherwise if we keep
                // scrolling down the dataset in our recyclerview will just keep growing and
                //growing and used up  lot of unnecessary memory. So this way we only have
                //maximum of 100 items in our recyclerview.
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashPagingSource(unsplashApi,query) }
        ).liveData


}