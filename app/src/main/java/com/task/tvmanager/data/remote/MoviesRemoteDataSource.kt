package com.task.tvmanager.data.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.task.tvmanager.CreateMovieMutation
import com.task.tvmanager.FetchMoviesPaginatedQuery
import com.task.tvmanager.type.CreateMovieInput
import com.task.tvmanager.type.MovieOrder
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) : BaseDataSource() {

    suspend fun fetchMoviesPaginated(
        order: List<MovieOrder>?,
        after: String?,
        first: Int?
    ) = getResult { apolloClient.query(FetchMoviesPaginatedQuery(
        order = Input.optional(order),
        after = Input.optional(after),
        first = Input.optional(first)

    )).await() }

    suspend fun addMovie(movieInput: CreateMovieInput) = getResult { apolloClient.mutate(CreateMovieMutation(movieInput)).await() }

}