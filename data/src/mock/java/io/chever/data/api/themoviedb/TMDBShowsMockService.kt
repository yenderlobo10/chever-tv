package io.chever.data.api.themoviedb

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.chever.data.R
import io.chever.data.api.BaseMockService
import io.chever.data.api.themoviedb.model.TMCollectionResponse
import io.chever.data.api.themoviedb.model.TMCreditsResponse
import io.chever.data.api.themoviedb.model.show.TMShowDetailResponse
import io.chever.data.api.themoviedb.model.show.TMShowResponse
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import javax.inject.Inject

class TMDBShowsMockService @Inject constructor(
    @ApplicationContext context: Context
) : BaseMockService(context) {

    fun details(): AppResult<AppFailure, TMShowDetailResponse> =
        this.readJsonToMockResponse(
            rawJsonId = R.raw.tmdb_show_detail_response,
            errorMessage = "Json mock response fail in show @details",
            responseType = TMShowDetailResponse::class.java
        )

    fun credits(): AppResult<AppFailure, TMCreditsResponse> =
        this.readJsonToMockResponse(
            rawJsonId = R.raw.tmdb_show_credits_response,
            errorMessage = "Json mock response fail in show @credits",
            responseType = TMCreditsResponse::class.java
        )

    fun recommendations(): AppResult<AppFailure, TMCollectionResponse<TMShowResponse>> =
        this.readJsonToMockResponse(
            rawJsonId = R.raw.tmdb_show_recommendations_response,
            errorMessage = "Json mock response fail in show @recommendations",
            responseTypes = arrayOf(
                TMCollectionResponse::class.java,
                TMShowResponse::class.java
            )
        )
}