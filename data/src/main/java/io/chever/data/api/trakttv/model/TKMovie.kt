package io.chever.data.api.trakttv.model

/**
 * Trakt.tv standard movie object with extended info.
 */
data class TKMovie(

    val title: String,
    val year: Int?,
    val ids: TKMovieIds,
    val rating: Float,
    val votes: Long,
    val certification: String?
)