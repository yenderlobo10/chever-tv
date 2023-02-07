package io.chever.data.repository

import io.chever.data.api.themoviedb.TMDBTrendingMockService
import io.chever.data.transform.mapToMediaItemList
import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.enums.TimeWindowEnum
import io.chever.domain.model.collection.MediaItem
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import io.chever.domain.repository.CollectionRepository
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val trendingService: TMDBTrendingMockService
) : CollectionRepository {

    override suspend fun trending(
        timeWindow: TimeWindowEnum,
        mediaType: MediaTypeEnum
    ): AppResult<AppFailure, List<MediaItem>> = when (
        val response = trendingService.trending()
    ) {

        is AppResult.Success -> AppResult.Success(
            response.value.results.mapToMediaItemList()
        )

        is AppResult.Failure -> AppResult.Failure(response.value)
    }

}