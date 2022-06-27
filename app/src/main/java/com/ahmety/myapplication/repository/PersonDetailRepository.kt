package com.ahmety.myapplication.repository

import com.ahmety.myapplication.api.MovieAppService
import com.ahmety.myapplication.model.Person
import com.ahmety.myapplication.model.PersonVideoResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonDetailRepository @Inject constructor(
    private val movieAppService: MovieAppService
) {
    suspend fun getPersonDetail(personId: String, apiKey: String): Response<Person> = withContext(
        Dispatchers.IO
    ) {
        val person = movieAppService.getPersonDetail(personId, apiKey)
        person
    }

    suspend fun getPersonCastMovie(personId: String, apiKey: String): Response<PersonVideoResult> = withContext(
        Dispatchers.IO
    ) {
        val person = movieAppService.getPersonCastMovie(personId, apiKey)
        person
    }

    suspend fun getPersonCastTv(personId: String, apiKey: String): Response<PersonVideoResult> = withContext(
        Dispatchers.IO
    ) {
        val person = movieAppService.getPersonCastTv(personId, apiKey)
        person
    }
}