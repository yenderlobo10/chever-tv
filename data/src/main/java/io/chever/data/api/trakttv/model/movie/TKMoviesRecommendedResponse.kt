package io.chever.data.api.trakttv.model.movie

import com.squareup.moshi.Json

/**
 * Trakt.tv movies recommended object response.
 */
data class TKMoviesRecommendedResponse(

    @Json(name = "user_count")
    val userCount: Int,

    val movie: TKMovieResponse,
)