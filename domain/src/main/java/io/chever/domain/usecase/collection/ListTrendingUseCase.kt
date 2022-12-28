package io.chever.domain.usecase.collection

import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.enums.TimeWindowEnum
import io.chever.domain.model.collection.MediaItem
import io.chever.domain.model.result.AppFailure
import io.chever.domain.model.result.AppResult
import io.chever.domain.repository.CollectionRepository
import io.chever.domain.usecase.base.UseCaseParams
import io.chever.shared.observability.AppLogger
import javax.inject.Inject

class ListTrendingUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository,
) : UseCaseParams<ListTrendingUseCase.Params, List<MediaItem>>() {

    override suspend fun run(
        params: Params
    ): AppResult<AppFailure, List<MediaItem>> {

        return try {

            collectionRepository.trending(
                timeWindow = params.timeWindow,
                mediaType = params.mediaType
            )

        } catch (ex: Exception) {

            AppLogger.error(ex)
            AppResult.Failure(ListTrendingError(ex))
        }
    }


    data class Params(
        val mediaType: MediaTypeEnum = MediaTypeEnum.All,
        val timeWindow: TimeWindowEnum
    )

    data class ListTrendingError(
        val error: Exception
    ) : AppFailure.FeatureFailure()
}