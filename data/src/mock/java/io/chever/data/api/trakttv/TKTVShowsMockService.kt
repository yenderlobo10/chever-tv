package io.chever.data.api.trakttv

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.chever.data.R
import io.chever.data.api.BaseMockService
import io.chever.data.api.trakttv.model.show.TKShowResponse
import io.chever.data.api.trakttv.model.show.TKShowsAnticipatedResponse
import io.chever.data.api.trakttv.model.show.TKShowsCollectionResponse
import io.chever.data.api.trakttv.model.show.TKShowsRecommendedResponse
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import javax.inject.Inject

class TKTVShowsMockService @Inject constructor(
    @ApplicationContext context: Context
) : BaseMockService(context) {

    fun watched(): AppResult<AppFailure, List<TKShowsCollectionResponse>> =
        this.readJsonToMockResponse(
            rawJsonId = R.raw.tktv_shows_watched,
            errorMessage = "Json mock response fail in shows @watched",
            responseTypes = arrayOf(List::class.java, TKShowsCollectionResponse::class.java)
        )

    fun recommended(): AppResult<AppFailure, List<TKShowsRecommendedResponse>> =
        this.readJsonToMockResponse(
            rawJsonId = R.raw.tktv_shows_recommended,
            errorMessage = "Json mock response fail in shows @recommended",
            responseTypes = arrayOf(List::class.java, TKShowsRecommendedResponse::class.java)
        )

    fun collected(): AppResult<AppFailure, List<TKShowsCollectionResponse>> =
        this.readJsonToMockResponse(
            rawJsonId = R.raw.tktv_shows_collected,
            errorMessage = "Json mock response fail in shows @collected",
            responseTypes = arrayOf(List::class.java, TKShowsCollectionResponse::class.java)
        )

    fun popular(): AppResult<AppFailure, List<TKShowResponse>> =
        this.readJsonToMockResponse(
            rawJsonId = R.raw.tktv_shows_popular,
            errorMessage = "Json mock response fail in shows @popular",
            responseTypes = arrayOf(List::class.java, TKShowResponse::class.java)
        )

    fun anticipated(): AppResult<AppFailure, List<TKShowsAnticipatedResponse>> =
        this.readJsonToMockResponse(
            rawJsonId = R.raw.tktv_shows_anticipated,
            errorMessage = "Json mock response fail in shows @anticipated",
            responseTypes = arrayOf(List::class.java, TKShowsAnticipatedResponse::class.java)
        )
}