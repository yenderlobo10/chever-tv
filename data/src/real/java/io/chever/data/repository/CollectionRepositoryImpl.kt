package io.chever.data.repository

import io.chever.data.api.themoviedb.service.TMDBTrendingService
import io.chever.data.transform.mapToMediaItemList
import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.enums.TimeWindowEnum
import io.chever.domain.model.collection.MediaItem
import io.chever.domain.model.result.AppFailure
import io.chever.domain.model.result.AppResult
import io.chever.domain.repository.CollectionRepository
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val tmdbTrendingService: TMDBTrendingService
) : CollectionRepository {

    override suspend fun trending(
        timeWindow: TimeWindowEnum,
        mediaType: MediaTypeEnum
    ): AppResult<AppFailure, List<MediaItem>> {

        val response = tmdbTrendingService.trending(
            timeWindow = timeWindow.value,
            mediaType = mediaType.value
        )

        val isSuccess = response.isSuccessful.and(response.body() != null)

        return when {
            isSuccess -> AppResult.Success(
                response.body()!!.results.mapToMediaItemList()
            )
            else -> AppResult.Failure(AppFailure.ServerError)
        }
    }
}