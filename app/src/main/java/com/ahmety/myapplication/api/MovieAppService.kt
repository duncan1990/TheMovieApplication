package com.ahmety.myapplication.api

import com.ahmety.myapplication.model.Person
import com.ahmety.myapplication.model.PersonVideoResult
import com.ahmety.myapplication.model.Result
import com.ahmety.myapplication.model.ResultMovieVideos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAppService {
    companion object {
        const val ENDPOINT = "https://api.themoviedb.org/3/"
    }

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): Response<Result>

    @GET("search/multi")
    suspend fun searchQueryKeywords(@Query("api_key") apiKey: String, @Query("query") query: String): Response<Result>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(@Path("movie_id") movieId: String, @Query("api_key") apiKey: String): Response<ResultMovieVideos>

    @GET("person/{person_id}")
    suspend fun getPersonDetail(@Path("person_id") personId: String, @Query("api_key") apiKey: String): Response<Person>

    @GET("person/{person_id}/movie_credits")
    suspend fun getPersonCastMovie(@Path("person_id") personId: String, @Query("api_key") apiKey: String): Response<PersonVideoResult>

    @GET("person/{person_id}/tv_credits")
    suspend fun getPersonCastTv(@Path("person_id") personId: String, @Query("api_key") apiKey: String): Response<PersonVideoResult>
}