package io.chever.data.api.trakttv.model.movie

/**
 * Trakt.tv movie rating object.
 */
data class TKMovieRatingResponse(

    val rating: Float,
    val votes: Long,
)