package com.task.tvmanager.ui.addshow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.tvmanager.CreateMovieMutation
import com.task.tvmanager.data.repository.MoviesRepository
import com.task.tvmanager.type.CreateMovieInput
import com.task.tvmanager.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddShowViewModel @Inject constructor(private val repository: MoviesRepository) : ViewModel() {

    private val _addMovieResult = MutableLiveData<Resource<CreateMovieMutation.Data>>()
    val addMovieResult : MutableLiveData<Resource<CreateMovieMutation.Data>>
        get() = _addMovieResult

    fun addMovie(movieInput: CreateMovieInput) {
        viewModelScope.launch {
            _addMovieResult.postValue(Resource.loading())
            val response = withContext(Dispatchers.IO) {
                 repository.addMovie(movieInput)
            }
            _addMovieResult.postValue(response)
        }
    }
}