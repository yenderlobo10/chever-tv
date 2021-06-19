package io.chever.tv.api.trakttv.domain.models

import com.squareup.moshi.Json

/**
 * Trakt.tv movies anticipated object.
 */
data class TKMovieAnticipated(

    @Json(name = "list_count")
    val listCount: Int,

    val movie: TKMovie,
)