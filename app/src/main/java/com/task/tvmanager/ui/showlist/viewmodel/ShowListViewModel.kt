package com.task.tvmanager.ui.showlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.tvmanager.FetchMoviesPaginatedQuery
import com.task.tvmanager.data.repository.MoviesRepository
import com.task.tvmanager.type.MovieOrder
import com.task.tvmanager.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ShowListViewModel @Inject constructor(private val repository: MoviesRepository) : ViewModel() {

    private val _paginatedMovies = MutableLiveData<Resource<FetchMoviesPaginatedQuery.Data>>()
    val paginatedMovies: LiveData<Resource<FetchMoviesPaginatedQuery.Data>>
        get() = _paginatedMovies

    private val order = MutableLiveData(listOf(MovieOrder.CREATEDAT_DESC))
    private val first = MutableLiveData(10)
    val hasNext = MutableLiveData(true)
    val endCursor = MutableLiveData<String>()

    init {
        fetch()
    }

    fun fetchMoviesPaginated(
        order: List<MovieOrder>? = null,
        after: String? = null,
        first: Int? = null
    ) {
        viewModelScope.launch {
            _paginatedMovies.postValue(Resource.loading())
            val response = withContext(Dispatchers.IO) {
                repository.fetchMoviesPaginated(order, after, first)
            }
            _paginatedMovies.postValue(response)
        }
    }

    fun fetch() {
        if (hasNext.value!!) {
            fetchMoviesPaginated(
                order = order.value,
                after = endCursor.value,
                first = first.value
            )
        }
    }
}