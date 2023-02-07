package io.chever.data.api.trakttv.model.movies

/**
 * Trakt.tv movie rating object.
 */
data class TKMovieRatingResponse(

    val rating: Float,
    val votes: Long,
)