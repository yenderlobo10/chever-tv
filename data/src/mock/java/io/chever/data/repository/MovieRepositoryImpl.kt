package io.chever.data.repository

import io.chever.data.api.themoviedb.TMDBMoviesMockService
import io.chever.data.api.themoviedb.mapper.detail.mapToPersonCastList
import io.chever.data.api.themoviedb.mapper.movie.mapToMediaItemList
import io.chever.data.api.themoviedb.mapper.movie.mapToMovieDetail
import io.chever.data.api.trakttv.TKTVMoviesMockService
import io.chever.data.api.trakttv.mapper.movie.mapToMediaItemList
import io.chever.domain.enums.TimeWindowEnum
import io.chever.domain.model.detail.PersonCast
import io.chever.domain.model.media.MediaItem
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
    ): AppResult<AppFailure, List<MediaItem>> =
        tktvMoviesService.watched().mapSuccessTo { it.mapToMediaItemList() }

    override suspend fun recommended(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>> =
        tktvMoviesService.recommended().mapSuccessTo { it.mapToMediaItemList() }

    override suspend fun collected(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>> =
        tktvMoviesService.collected().mapSuccessTo { it.mapToMediaItemList() }

    override suspend fun popular(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>> =
        tktvMoviesService.popular().mapSuccessTo { it.mapToMediaItemList() }

    override suspend fun anticipated(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>> =
        tktvMoviesService.anticipated().mapSuccessTo { it.mapToMediaItemList() }

    override suspend fun details(
        id: Long
    ): AppResult<AppFailure, MovieDetail> =
        tmdbMoviesService.details().mapSuccessTo { it.mapToMovieDetail() }

    override suspend fun credits(
        id: Long
    ): AppResult<AppFailure, List<PersonCast>> =
        tmdbMoviesService.credits().mapSuccessTo { it.mapToPersonCastList() }

    override suspend fun recommendations(
        id: Long
    ): AppResult<AppFailure, List<MediaItem>> =
        tmdbMoviesService.recommendations().mapSuccessTo { it.mapToMediaItemList() }
}