package io.chever.tv.api.trakttv.domain.models

import com.squareup.moshi.Json

/**
 * Trakt.tv movies recommended object.
 */
data class TKMovieRecommended(

    @Json(name = "user_count")
    val userCount: Int,

    val movie: TKMovie,
)