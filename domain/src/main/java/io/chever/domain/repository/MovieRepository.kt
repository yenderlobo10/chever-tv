package io.chever.domain.repository

import io.chever.domain.model.movie.MovieDetail
import io.chever.domain.model.result.AppFailure
import io.chever.domain.model.result.AppResult

interface MovieRepository {

    suspend fun details(
        id: Long
    ): AppResult<AppFailure, MovieDetail>
}