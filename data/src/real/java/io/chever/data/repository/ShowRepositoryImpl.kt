package io.chever.data.repository

import io.chever.data.api.themoviedb.service.TMDBShowsService
import io.chever.data.api.trakttv.service.TKTVShowsService
import io.chever.domain.enums.TimeWindowEnum
import io.chever.domain.model.collection.MediaItem
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import io.chever.domain.model.show.ShowDetail
import io.chever.domain.repository.ShowRepository
import javax.inject.Inject

class ShowRepositoryImpl @Inject constructor(
    private val tmdbShowsService: TMDBShowsService,
    private val tktvShowsService: TKTVShowsService
) : ShowRepository {

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
    ): AppResult<AppFailure, ShowDetail> {
        TODO("Not yet implemented")
    }
}