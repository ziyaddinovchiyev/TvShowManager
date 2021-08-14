package com.task.tvmanager.utils

import okhttp3.Interceptor
import okhttp3.Response

class ApolloHeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-Parse-Client-Key", Constants.CLIENT_KEY)
            .addHeader("X-Parse-Application-Id", Constants.APPLICATION_ID)
            .build()

        return chain.proceed(request)
    }
}