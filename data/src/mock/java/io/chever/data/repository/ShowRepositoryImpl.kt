package io.chever.data.repository

import io.chever.data.api.themoviedb.TMDBShowsMockService
import io.chever.data.api.themoviedb.mapper.detail.mapToPersonCastList
import io.chever.data.api.themoviedb.mapper.show.mapToMediaItemList
import io.chever.data.api.themoviedb.mapper.show.mapToShowDetail
import io.chever.data.api.trakttv.TKTVShowsMockService
import io.chever.data.api.trakttv.mapper.show.mapToMediaItemList
import io.chever.domain.enums.TimeWindowEnum
import io.chever.domain.model.detail.PersonCast
import io.chever.domain.model.media.MediaItem
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
    ): AppResult<AppFailure, List<MediaItem>> =
        tktvShowsService.watched().mapSuccessTo { it.mapToMediaItemList() }

    override suspend fun recommended(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>> =
        tktvShowsService.recommended().mapSuccessTo { it.mapToMediaItemList() }

    override suspend fun collected(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>> =
        tktvShowsService.collected().mapSuccessTo { it.mapToMediaItemList() }

    override suspend fun popular(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>> =
        tktvShowsService.popular().mapSuccessTo { it.mapToMediaItemList() }

    override suspend fun anticipated(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>> =
        tktvShowsService.anticipated().mapSuccessTo { it.mapToMediaItemList() }

    override suspend fun details(
        id: Long
    ): AppResult<AppFailure, ShowDetail> =
        tmdbShowsMockService.details().mapSuccessTo { it.mapToShowDetail() }

    override suspend fun credits(
        id: Long
    ): AppResult<AppFailure, List<PersonCast>> =
        tmdbShowsMockService.credits().mapSuccessTo { it.mapToPersonCastList() }

    override suspend fun recommendations(
        id: Long
    ): AppResult<AppFailure, List<MediaItem>> =
        tmdbShowsMockService.recommendations().mapSuccessTo { it.mapToMediaItemList() }
}