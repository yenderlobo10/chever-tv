package io.chever.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.chever.data.api.themoviedb.TMDBApiClient
import io.chever.data.api.themoviedb.service.TMDBMoviesService
import io.chever.data.api.themoviedb.service.TMDBShowsService
import io.chever.data.api.themoviedb.service.TMDBTrendingService

@Module
@InstallIn(SingletonComponent::class)
object TMDBApiServicesModule {

    @Provides
    fun provideMoviesService(
        apiClient: TMDBApiClient
    ): TMDBMoviesService =
        apiClient.retrofit().create(TMDBMoviesService::class.java)

    @Provides
    fun provideShowsService(
        apiClient: TMDBApiClient
    ): TMDBShowsService =
        apiClient.retrofit().create(TMDBShowsService::class.java)

    @Provides
    fun provideTrendingService(
        apiClient: TMDBApiClient
    ): TMDBTrendingService =
        apiClient.retrofit().create(TMDBTrendingService::class.java)

}