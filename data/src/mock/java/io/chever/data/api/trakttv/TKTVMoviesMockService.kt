package io.chever.data.api.trakttv

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.chever.data.R
import io.chever.data.api.BaseMockService
import io.chever.data.api.trakttv.model.movie.TKMovieResponse
import io.chever.data.api.trakttv.model.movie.TKMoviesAnticipatedResponse
import io.chever.data.api.trakttv.model.movie.TKMoviesCollectionResponse
import io.chever.data.api.trakttv.model.movie.TKMoviesRecommendedResponse
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import javax.inject.Inject

class TKTVMoviesMockService @Inject constructor(
    @ApplicationContext context: Context
) : BaseMockService(context) {

    fun watched(): AppResult<AppFailure, List<TKMoviesCollectionResponse>> =
        this.readJsonToMockResponse(
            rawJsonId = R.raw.tktv_movies_watched,
            errorMessage = "Json mock response fail in movies @watched",
            responseTypes = arrayOf(List::class.java, TKMoviesCollectionResponse::class.java)
        )

    fun recommended(): AppResult<AppFailure, List<TKMoviesRecommendedResponse>> =
        this.readJsonToMockResponse(
            rawJsonId = R.raw.tktv_movies_recommended,
            errorMessage = "Json mock response fail in movies @recommended",
            responseTypes = arrayOf(List::class.java, TKMoviesRecommendedResponse::class.java)
        )

    fun collected(): AppResult<AppFailure, List<TKMoviesCollectionResponse>> =
        this.readJsonToMockResponse(
            rawJsonId = R.raw.tktv_movies_collected,
            errorMessage = "Json mock response fail in movies @collected",
            responseTypes = arrayOf(List::class.java, TKMoviesCollectionResponse::class.java)
        )

    fun popular(): AppResult<AppFailure, List<TKMovieResponse>> =
        this.readJsonToMockResponse(
            rawJsonId = R.raw.tktv_movies_popular,
            errorMessage = "Json mock response fail in movies @popular",
            responseTypes = arrayOf(List::class.java, TKMovieResponse::class.java)
        )

    fun anticipated(): AppResult<AppFailure, List<TKMoviesAnticipatedResponse>> =
        this.readJsonToMockResponse(
            rawJsonId = R.raw.tktv_movies_anticipated,
            errorMessage = "Json mock response fail in movies @anticipated",
            responseTypes = arrayOf(List::class.java, TKMoviesAnticipatedResponse::class.java)
        )
}