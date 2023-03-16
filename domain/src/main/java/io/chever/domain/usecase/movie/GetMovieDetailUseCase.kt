package io.chever.domain.usecase.movie

import io.chever.domain.model.movie.MovieDetail
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import io.chever.domain.repository.MovieRepository
import io.chever.shared.observability.AppLogger
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(
        id: Long
    ): AppResult<AppFailure, MovieDetail> {

        return try {

            movieRepository.details(id)

        } catch (ex: Exception) {

            AppLogger.error(ex)
            AppResult.Failure(GetMovieDetailFailure(ex))
        }
    }

    data class GetMovieDetailFailure(
        val error: Throwable
    ) : AppFailure.FeatureFailure()
}