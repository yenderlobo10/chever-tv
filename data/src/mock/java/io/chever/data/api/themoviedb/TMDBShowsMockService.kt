package io.chever.data.api.themoviedb

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.chever.data.R
import io.chever.data.api.BaseMockService
import io.chever.data.api.themoviedb.model.shows.TMShowDetailResponse
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
}