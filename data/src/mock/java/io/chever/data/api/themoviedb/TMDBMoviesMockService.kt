package io.chever.data.api.themoviedb

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.chever.data.R
import io.chever.data.api.BaseMockService
import io.chever.data.api.themoviedb.model.movies.TMMovieDetailResponse
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import javax.inject.Inject

class TMDBMoviesMockService @Inject constructor(
    @ApplicationContext context: Context
) : BaseMockService(context) {

    fun details(): AppResult<AppFailure, TMMovieDetailResponse> =
        this.readJsonToMockResponse(
            rawJsonId = R.raw.tmdb_movie_detail_response,
            errorMessage = "Json mock response fail in movie @details",
            responseType = TMMovieDetailResponse::class.java
        )
}