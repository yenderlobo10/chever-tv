package io.chever.data.api.themoviedb

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.chever.data.R
import io.chever.data.api.BaseMockService
import io.chever.data.api.themoviedb.model.TMMovieDetail
import io.chever.domain.model.result.AppFailure
import io.chever.domain.model.result.AppResult
import javax.inject.Inject

class TMDBMovieService @Inject constructor(
    @ApplicationContext context: Context
) : BaseMockService(context) {

    fun details(): AppResult<AppFailure, TMMovieDetail> =
        this.readJsonToMockResponse(
            rawJsonId = R.raw.tmdb_movie_detail_response,
            errorMessage = "Json mock response fail in @details",
            responseType = TMMovieDetail::class.java
        )
}