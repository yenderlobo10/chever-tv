package io.chever.domain.usecase.movie

import io.chever.domain.model.detail.PersonCast
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import io.chever.domain.repository.MovieRepository
import io.chever.shared.observability.AppLogger
import javax.inject.Inject

class GetMovieCreditsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(
        id: Long
    ): AppResult<AppFailure, List<PersonCast>> = try {

        movieRepository.credits(id)

    } catch (ex: Exception) {

        AppLogger.error(ex)
        AppResult.Failure(GetMovieCreditsFailure(ex))
    }

    data class GetMovieCreditsFailure(
        val error: Throwable
    ) : AppFailure.FeatureFailure()
}