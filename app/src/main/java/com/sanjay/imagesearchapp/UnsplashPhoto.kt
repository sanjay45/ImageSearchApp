package com.sanjay.imagesearchapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashPhoto(
    val id: String,
    val description: String?,// this is nullable because json might not contain the description
    val urls: UnsplashPhotoUrls,
    val user: UnsplashPhotoUser
) : Parcelable // we implement UnsplashPhoto with Parcelable interface because when we later in our
// app navigate to the detail screen of an image we want to send this whole UnsplashPhoto object
// over to this detail screen to use this data there.
{

    @Parcelize
    data class UnsplashPhotoUrls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String
    ) : Parcelable

    @Parcelize
    data class UnsplashPhotoUser(
        val name: String,
        val username: String
    ) : Parcelable {
        /**This property belong to the body of UnsplashPhotoUser because this is the computed
         * property which means it generated directly from another value from the username to
         * more exact */
        val attributionUrls
            get() = "https://unsplash.com/$username?utm_source=ImageSearchApp" +
                    "&utm_medium=referral"
    }
}
