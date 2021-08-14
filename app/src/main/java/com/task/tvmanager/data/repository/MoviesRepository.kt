package com.task.tvmanager.data.repository

import com.task.tvmanager.CreateMovieMutation
import com.task.tvmanager.FetchMoviesPaginatedQuery
import com.task.tvmanager.data.remote.MoviesRemoteDataSource
import com.task.tvmanager.type.CreateMovieInput
import com.task.tvmanager.type.MovieOrder
import com.task.tvmanager.utils.Resource
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remoteDataSource: MoviesRemoteDataSource
) {
    suspend fun fetchMoviesPaginated(
        order: List<MovieOrder>?,
        after: String?,
        first: Int?
    ): Resource<FetchMoviesPaginatedQuery.Data> = remoteDataSource.fetchMoviesPaginated(
        order = order,
        after = after,
        first = first)

    suspend fun addMovie(movieInput: CreateMovieInput): Resource<CreateMovieMutation.Data> = remoteDataSource.addMovie(movieInput)
}