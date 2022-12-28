package io.chever.data.api.trakttv.model

import com.squareup.moshi.Json

/**
 * Trakt.tv movies recommended object.
 */
data class TKMovieRecommendedResult(

    @Json(name = "user_count")
    val userCount: Int,

    val movie: TKMovie,
)