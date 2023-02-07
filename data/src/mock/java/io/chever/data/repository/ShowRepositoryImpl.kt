package io.chever.data.repository

import io.chever.data.api.themoviedb.TMDBShowsMockService
import io.chever.data.api.trakttv.TKTVShowsMockService
import io.chever.data.transform.mapToMediaItemList
import io.chever.data.transform.mapToShowDetail
import io.chever.domain.enums.TimeWindowEnum
import io.chever.domain.model.collection.MediaItem
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import io.chever.domain.model.show.ShowDetail
import io.chever.domain.repository.ShowRepository
import javax.inject.Inject

class ShowRepositoryImpl @Inject constructor(
    private val tmdbShowsMockService: TMDBShowsMockService,
    private val tktvShowsService: TKTVShowsMockService
) : ShowRepository {

    override suspend fun watched(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>> = when (
        val response = tktvShowsService.watched()
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
        val response = tktvShowsService.recommended()
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
        val response = tktvShowsService.collected()
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
        val response = tktvShowsService.popular()
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
        val response = tktvShowsService.anticipated()
    ) {

        is AppResult.Success -> AppResult.Success(
            response.value.mapToMediaItemList()
        )

        is AppResult.Failure -> AppResult.Failure(response.value)
    }

    override suspend fun details(
        id: Long
    ): AppResult<AppFailure, ShowDetail> = when (
        val response = tmdbShowsMockService.details()
    ) {

        is AppResult.Success -> AppResult.Success(
            response.value.mapToShowDetail()
        )

        is AppResult.Failure -> AppResult.Failure(response.value)
    }
}