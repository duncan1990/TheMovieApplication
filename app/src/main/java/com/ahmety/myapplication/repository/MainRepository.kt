package com.ahmety.myapplication.repository

import com.ahmety.myapplication.api.MovieAppService
import com.ahmety.myapplication.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val movieAppService: MovieAppService
) {
    suspend fun getPopularMovies(apiKey: String): Response<Result> = withContext(
        Dispatchers.IO
    ) {
        val movies = movieAppService.getPopularMovies(apiKey = apiKey)
        movies
    }

    suspend fun searchQueryKeywords(apiKey: String, query: String): Response<Result> = withContext(
        Dispatchers.IO
    ) {
        val data = movieAppService.searchQueryKeywords(apiKey = apiKey, query = query)
        data
    }

}