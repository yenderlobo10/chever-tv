package io.chever.data.api.trakttv.model.movie

import com.squareup.moshi.Json

/**
 * Trakt.tv movies anticipated object response.
 */
data class TKMoviesAnticipatedResponse(

    @Json(name = "list_count")
    val listCount: Int,

    val movie: TKMovieResponse
)