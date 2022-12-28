package io.chever.data.api.themoviedb

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.chever.data.R
import io.chever.data.api.BaseMockService
import io.chever.data.api.themoviedb.model.TMObjectListResult
import io.chever.data.api.themoviedb.model.TMTrending
import io.chever.domain.model.result.AppFailure
import io.chever.domain.model.result.AppResult
import javax.inject.Inject

class TMDBTrendingService @Inject constructor(
    @ApplicationContext context: Context
) : BaseMockService(context) {

    fun trending(): AppResult<AppFailure, TMObjectListResult<TMTrending>> =
        this.readJsonToMockResponse(
            rawJsonId = R.raw.tmdb_trending_response,
            errorMessage = "Json mock response fail in @trending",
            responseTypes = arrayOf(
                TMObjectListResult::class.java,
                TMTrending::class.java
            )
        )
}