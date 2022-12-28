package io.chever.data.repository

import io.chever.domain.model.movie.MovieDetail
import io.chever.domain.model.result.AppFailure
import io.chever.domain.model.result.AppResult
import io.chever.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(

): MovieRepository {

    override suspend fun details(id: Long): AppResult<AppFailure, MovieDetail> {
        TODO("Not yet implemented")
    }
}