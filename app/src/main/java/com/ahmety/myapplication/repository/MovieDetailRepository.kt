package com.ahmety.myapplication.repository

import com.ahmety.myapplication.api.MovieAppService
import com.ahmety.myapplication.model.ResultMovieVideos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailRepository @Inject constructor(
    private val movieAppService: MovieAppService
) {
    suspend fun getMovieVideos(movieId: String, apiKey: String): Response<ResultMovieVideos> = withContext(
        Dispatchers.IO
    ) {
        val videos = movieAppService.getMovieVideos(movieId, apiKey)
        videos
    }
}