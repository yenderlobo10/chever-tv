package io.chever.data.repository

import io.chever.data.api.themoviedb.TMDBMovieService
import io.chever.data.transform.mapToMovieDetail
import io.chever.domain.model.movie.MovieDetail
import io.chever.domain.model.result.AppFailure
import io.chever.domain.model.result.AppResult
import io.chever.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieService: TMDBMovieService
) : MovieRepository {

    override suspend fun details(
        id: Long
    ): AppResult<AppFailure, MovieDetail> = when (
        val response = movieService.details()
    ) {

        is AppResult.Success -> AppResult.Success(
            response.value.mapToMovieDetail()
        )

        is AppResult.Failure -> AppResult.Failure(response.value)
    }

}