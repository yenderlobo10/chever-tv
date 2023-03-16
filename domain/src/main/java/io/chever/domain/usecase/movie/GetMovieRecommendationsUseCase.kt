package io.chever.domain.usecase.movie

import io.chever.domain.model.media.MediaItem
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import io.chever.domain.repository.MovieRepository
import io.chever.domain.usecase.media.ListMediaItemsWithDetailUseCase
import io.chever.shared.observability.AppLogger
import javax.inject.Inject

class GetMovieRecommendationsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val listMediaItemsWithDetailUseCase: ListMediaItemsWithDetailUseCase
) {

    suspend operator fun invoke(
        id: Long
    ): AppResult<AppFailure, List<MediaItem>> = try {

        listMediaItemsWithDetailUseCase {
            movieRepository.recommendations(id)
        }

    } catch (ex: Exception) {

        AppLogger.error(ex)
        AppResult.Failure(ListMovieRecommendationsFailure(ex))
    }

    data class ListMovieRecommendationsFailure(
        val error: Throwable
    ) : AppFailure.FeatureFailure()
}