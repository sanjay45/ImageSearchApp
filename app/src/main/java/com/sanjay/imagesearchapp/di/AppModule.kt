package com.sanjay.imagesearchapp.di

import com.sanjay.imagesearchapp.api.UnsplashApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)// this annotation tells dagger in what scope we want to use
// this object because can live in the lifecycle of an activity or a fragment. In our case,
//we want to have this object available in the lifetime of our whole app and not only that we also
//want singleton of these objects so we only want one Retrofit instance and one UnsplashApi instance
//throughout our wholeapp because it does not make sense to create multiple of them which just be
// a waste of memory.
/**We could also use class keyword but object makes the  generated dagger code more efficient*/
object AppModule {

    @Provides // this tells dagger how to create an object. In this case, Retrofit object
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(UnsplashApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUnsplashApi(retrofit: Retrofit): UnsplashApi =
        retrofit.create(UnsplashApi::class.java)
}