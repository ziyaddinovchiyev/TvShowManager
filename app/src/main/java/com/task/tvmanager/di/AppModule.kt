package com.task.tvmanager.di

import com.apollographql.apollo.ApolloClient
import com.task.tvmanager.data.remote.MoviesRemoteDataSource
import com.task.tvmanager.data.repository.MoviesRepository
import com.task.tvmanager.utils.ApolloHeaderInterceptor
import com.task.tvmanager.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApolloHeaderInterceptor(): ApolloHeaderInterceptor = ApolloHeaderInterceptor()

    @Provides
    fun provideOkHttpClient(interceptor: ApolloHeaderInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(Constants.BASE_URL)
            .okHttpClient(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideMoviesRemoteDataSource(apolloClient: ApolloClient) : MoviesRemoteDataSource = MoviesRemoteDataSource(apolloClient)

    @Singleton
    @Provides
    fun provideMoviesRepository(moviesRemoteDataSource: MoviesRemoteDataSource) : MoviesRepository = MoviesRepository(moviesRemoteDataSource)
}