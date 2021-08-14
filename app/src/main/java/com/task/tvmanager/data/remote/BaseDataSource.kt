package com.task.tvmanager.data.remote

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.task.tvmanager.utils.Resource

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            val body = response.data
            if (body != null) return Resource.success(body)

            return error(" ${response.errors?.joinToString()}")
        } catch (e: ApolloException) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error("Network call has failed for a following reason: $message")
    }

}