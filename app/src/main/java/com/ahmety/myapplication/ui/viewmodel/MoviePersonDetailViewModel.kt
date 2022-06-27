package com.ahmety.myapplication.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmety.myapplication.model.Data
import com.ahmety.myapplication.model.Person
import com.ahmety.myapplication.model.PersonVideoResult
import com.ahmety.myapplication.model.ResultMovieVideos
import com.ahmety.myapplication.repository.MovieDetailRepository
import com.ahmety.myapplication.repository.PersonDetailRepository
import com.ahmety.myapplication.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MoviePersonDetailViewModel @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository,
    private val personDetailRepository: PersonDetailRepository
) : ViewModel() {
    var movieData: Data? = null
    var personData: Data? = null
    val videoList: MutableLiveData<Resource<ResultMovieVideos>> = MutableLiveData()
    val personDetailList: MutableLiveData<Resource<Person>> = MutableLiveData()
    val personCastMovieList: MutableLiveData<Resource<PersonVideoResult>> = MutableLiveData()
    val personCastTvList: MutableLiveData<Resource<PersonVideoResult>> = MutableLiveData()

    fun getMovieVideos() {
        videoList.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                val response =
                    movieDetailRepository.getMovieVideos(movieData?.id.toString(), "5edb1a16775e23c6542d07519328fdf1")
                response.body()?.let {
                    videoList.postValue(Resource.Success(it))
                }
            } catch (ex: Exception) {
                when (ex) {
                    is IOException -> videoList.postValue(Resource.Error("Network Failure " + ex.localizedMessage))
                    else -> videoList.postValue(Resource.Error("Conversion Error"))
                }
            }

        }
    }

    fun getPersonDetail() {
        personDetailList.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                val response =
                    personDetailRepository.getPersonDetail(personData?.id.toString(), "5edb1a16775e23c6542d07519328fdf1")
                response.body()?.let {
                    personDetailList.postValue(Resource.Success(it))
                }
            } catch (ex: Exception) {
                when (ex) {
                    is IOException -> personDetailList.postValue(Resource.Error("Network Failure " + ex.localizedMessage))
                    else -> personDetailList.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

    fun getPersonCastMovie() {
        personCastMovieList.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                val response =
                    personDetailRepository.getPersonCastMovie(personData?.id.toString(), "5edb1a16775e23c6542d07519328fdf1")
                response.body()?.let {
                    personCastMovieList.postValue(Resource.Success(it))
                }
            } catch (ex: Exception) {
                when (ex) {
                    is IOException -> personCastMovieList.postValue(Resource.Error("Network Failure " + ex.localizedMessage))
                    else -> personCastMovieList.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

    fun getPersonCastTv() {
        personCastTvList.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                val response =
                    personDetailRepository.getPersonCastTv(personData?.id.toString(), "5edb1a16775e23c6542d07519328fdf1")
                response.body()?.let {
                    personCastTvList.postValue(Resource.Success(it))
                }
            } catch (ex: Exception) {
                when (ex) {
                    is IOException -> personCastTvList.postValue(Resource.Error("Network Failure " + ex.localizedMessage))
                    else -> personCastTvList.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }
}