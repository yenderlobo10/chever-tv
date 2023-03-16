package io.chever.domain.usecase.show

import io.chever.domain.model.media.MediaItem
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import io.chever.domain.repository.ShowRepository
import io.chever.domain.usecase.media.ListMediaItemsWithDetailUseCase
import io.chever.shared.observability.AppLogger
import javax.inject.Inject

class GetShowRecommendationsUseCase @Inject constructor(
    private val showRepository: ShowRepository,
    private val listMediaItemsWithDetailUseCase: ListMediaItemsWithDetailUseCase
) {

    suspend operator fun invoke(
        id: Long
    ): AppResult<AppFailure, List<MediaItem>> = try {

        listMediaItemsWithDetailUseCase {
            showRepository.recommendations(id)
        }

    } catch (ex: Exception) {

        AppLogger.error(ex)
        AppResult.Failure(ListMovieRecommendationsFailure(ex))
    }

    data class ListMovieRecommendationsFailure(
        val error: Throwable
    ) : AppFailure.FeatureFailure()
}