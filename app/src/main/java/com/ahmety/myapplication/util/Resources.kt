package com.ahmety.myapplication.util

import com.ahmety.myapplication.model.MediaType

sealed class Resource<out T>(
    val data: T? = null,
    val mediaType: MediaType? = null,
    val message: String? = null
) {
    class Success<T>(data: T, mediaType: MediaType? = null): Resource<T>(data=data, mediaType=mediaType)
    class Error<T>(message: String, data: T? = null, mediaType: MediaType? = null): Resource<T>(data= data, message=message, mediaType=mediaType)
    class Loading<T> : Resource<T>()
}