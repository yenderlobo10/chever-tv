package io.chever.data.api.trakttv.model

import com.squareup.moshi.Json

/**
 * Trakt.tv movies anticipated object.
 */
data class TKMovieAnticipatedResult(

    @Json(name = "list_count")
    val listCount: Int,

    val movie: TKMovie,
)