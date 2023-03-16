package io.chever.data.api.themoviedb

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.chever.data.R
import io.chever.data.api.BaseMockService
import io.chever.data.api.themoviedb.model.TMCollectionResponse
import io.chever.data.api.themoviedb.model.TMCreditsResponse
import io.chever.data.api.themoviedb.model.movie.TMMovieDetailResponse
import io.chever.data.api.themoviedb.model.movie.TMMovieResponse
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

    fun credits(): AppResult<AppFailure, TMCreditsResponse> =
        this.readJsonToMockResponse(
            rawJsonId = R.raw.tmdb_movie_credits_response,
            errorMessage = "Json mock response fail in movie @credits",
            responseType = TMCreditsResponse::class.java
        )

    fun recommendations(): AppResult<AppFailure, TMCollectionResponse<TMMovieResponse>> =
        this.readJsonToMockResponse(
            rawJsonId = R.raw.tmdb_movie_recommendations_response,
            errorMessage = "Json mock response fail in movie @recommendations",
            responseTypes = arrayOf(
                TMCollectionResponse::class.java,
                TMMovieResponse::class.java
            )
        )
}