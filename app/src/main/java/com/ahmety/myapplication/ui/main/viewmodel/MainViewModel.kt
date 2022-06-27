package com.ahmety.myapplication.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmety.myapplication.model.MediaType
import com.ahmety.myapplication.model.Result
import com.ahmety.myapplication.repository.MainRepository
import com.ahmety.myapplication.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    val movieList: MutableLiveData<Resource<Result>> = MutableLiveData()

    fun getPopularMovies(){
        movieList.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                val response = mainRepository.getPopularMovies("5edb1a16775e23c6542d07519328fdf1")
                response.body()?.let {
                    movieList.postValue(Resource.Success(it))
                }
            } catch (ex: Exception) {
                when (ex) {
                    is IOException -> movieList.postValue(Resource.Error("Network Failure " +  ex.localizedMessage))
                    else -> movieList.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

    fun searchQueryKeywords(mediaType: MediaType, queryWord: String){
        movieList.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                val response = mainRepository.searchQueryKeywords("5edb1a16775e23c6542d07519328fdf1", queryWord)
                response.body()?.let {
                    movieList.postValue(Resource.Success(it,mediaType))
                }
            } catch (ex: Exception) {
                when (ex) {
                    is IOException -> movieList.postValue(Resource.Error("Network Failure " +  ex.localizedMessage))
                    else -> movieList.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

}