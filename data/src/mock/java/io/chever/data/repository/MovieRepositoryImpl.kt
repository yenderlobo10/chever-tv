package io.chever.data.repository

import io.chever.data.api.themoviedb.TMDBMoviesMockService
import io.chever.data.api.trakttv.TKTVMoviesMockService
import io.chever.data.transform.mapToMediaItemList
import io.chever.data.transform.mapToMovieDetail
import io.chever.domain.enums.TimeWindowEnum
import io.chever.domain.model.collection.MediaItem
import io.chever.domain.model.movie.MovieDetail
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import io.chever.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val tmdbMoviesService: TMDBMoviesMockService,
    private val tktvMoviesService: TKTVMoviesMockService
) : MovieRepository {

    override suspend fun watched(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>> = when (
        val response = tktvMoviesService.watched()
    ) {

        is AppResult.Success -> AppResult.Success(
            response.value.mapToMediaItemList()
        )

        is AppResult.Failure -> AppResult.Failure(response.value)
    }

    override suspend fun recommended(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>> = when (
        val response = tktvMoviesService.recommended()
    ) {

        is AppResult.Success -> AppResult.Success(
            response.value.mapToMediaItemList()
        )

        is AppResult.Failure -> AppResult.Failure(response.value)
    }

    override suspend fun collected(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>> = when (
        val response = tktvMoviesService.collected()
    ) {

        is AppResult.Success -> AppResult.Success(
            response.value.mapToMediaItemList()
        )

        is AppResult.Failure -> AppResult.Failure(response.value)
    }

    override suspend fun popular(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>> = when (
        val response = tktvMoviesService.popular()
    ) {

        is AppResult.Success -> AppResult.Success(
            response.value.mapToMediaItemList()
        )

        is AppResult.Failure -> AppResult.Failure(response.value)
    }

    override suspend fun anticipated(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>> = when (
        val response = tktvMoviesService.anticipated()
    ) {

        is AppResult.Success -> AppResult.Success(
            response.value.mapToMediaItemList()
        )

        is AppResult.Failure -> AppResult.Failure(response.value)
    }

    override suspend fun details(
        id: Long
    ): AppResult<AppFailure, MovieDetail> = when (
        val response = tmdbMoviesService.details()
    ) {

        is AppResult.Success -> AppResult.Success(
            response.value.mapToMovieDetail()
        )

        is AppResult.Failure -> AppResult.Failure(response.value)
    }

}