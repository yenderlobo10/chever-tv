package io.chever.data.repository

import io.chever.data.api.themoviedb.service.TMDBMoviesService
import io.chever.data.api.trakttv.service.TKTVMoviesService
import io.chever.domain.enums.TimeWindowEnum
import io.chever.domain.model.collection.MediaItem
import io.chever.domain.model.movie.MovieDetail
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import io.chever.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val tmdbMoviesService: TMDBMoviesService,
    private val tktvMoviesService: TKTVMoviesService
) : MovieRepository {

    override suspend fun watched(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun recommended(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun collected(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun popular(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun anticipated(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun details(
        id: Long
    ): AppResult<AppFailure, MovieDetail> {
        TODO("Not yet implemented")
    }
}