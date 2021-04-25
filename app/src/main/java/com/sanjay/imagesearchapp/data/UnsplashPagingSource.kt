package com.sanjay.imagesearchapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sanjay.imagesearchapp.api.UnsplashApi
import retrofit2.HttpException
import java.io.IOException

//PagingSource is a class that knows how to actually load data from our restapi and then turn it
//into pages
/** We do not use dagger to inject this class this is why we do not have @Inject constructor here
 * this is because the query is something we do not know at compile time we cannot dagger let
 * inject. We only know at runtime when we actually type the search into the app so we have to
 * pass it dynamically and for this we will later instantiate this class ourselves*/
/** PagingSource<key,value> takes two type parameter -
 * key -
 * The first type parameter is the kind of key we want to use to distinguish between different
 * pages and in most cases, the key type will be an int because we distinguish between the pages
 * by the number.
 *
 * value-
 * The second type parameter is the kind of data we want to use to filed these pages.
 * */

private const val UNSPLASH_STARTING_PAGE_INDEX = 1
class UnsplashPagingSource(
    private val unsplashApi: UnsplashApi,
    private val query:String
) : PagingSource<Int,UnsplashPhoto>() {

    /**load function will later actually trigger the api request and turn the data into pages.*/
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

       return try {
            val response = unsplashApi.searchPhotos(query,position,params.loadSize)
            val photos = response.results
           LoadResult.Page(
               data = photos,
               prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position-1,
               //nextKey is position+1 unless we reached the end of search results and we know
               // that we reached the end if the photos list that we get back is  empty.This is how
               //we know that we reached the end of the results.
               nextKey = if (photos.isEmpty()) null else position+1
           )

        }
       //IOException will be thrown for example when there is no internet connection while we are
       // trying to make the request.
       catch (e:IOException){
          LoadResult.Error(e)
        }
       // HttpException will be thrown if we made the request but there went something wrong on
       // the server. For example, if we are not authorized to do the request because we forgot to
       // pass the API_KEY or for example if there is no data on the endpoint that we try to make
       // a request on.
       catch (e:HttpException){
          LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        return null
    }

}