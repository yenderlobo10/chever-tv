package io.chever.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.chever.data.api.trakttv.TraktTVApiClient
import io.chever.data.api.trakttv.service.TKTVMoviesService
import io.chever.data.api.trakttv.service.TKTVShowsService

@Module
@InstallIn(SingletonComponent::class)
object TraktTVApiServicesModule {

    @Provides
    fun provideMoviesService(
        apiClient: TraktTVApiClient
    ): TKTVMoviesService =
        apiClient.retrofit().create(TKTVMoviesService::class.java)

    @Provides
    fun provideShowsService(
        apiClient: TraktTVApiClient
    ): TKTVShowsService =
        apiClient.retrofit().create(TKTVShowsService::class.java)
}