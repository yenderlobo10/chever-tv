package io.chever.data.api.themoviedb

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.chever.data.R
import io.chever.data.api.BaseMockService
import io.chever.data.api.themoviedb.model.TMCollectionResponse
import io.chever.data.api.themoviedb.model.TMTrendingResponse
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import javax.inject.Inject

class TMDBTrendingMockService @Inject constructor(
    @ApplicationContext context: Context
) : BaseMockService(context) {

    fun trending(): AppResult<AppFailure, TMCollectionResponse<TMTrendingResponse>> =
        this.readJsonToMockResponse(
            rawJsonId = R.raw.tmdb_trending_response,
            errorMessage = "Json mock response fail in @trending",
            responseTypes = arrayOf(
                TMCollectionResponse::class.java,
                TMTrendingResponse::class.java
            )
        )
}