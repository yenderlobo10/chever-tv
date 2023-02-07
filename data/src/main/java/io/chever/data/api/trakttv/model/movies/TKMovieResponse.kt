package io.chever.data.api.trakttv.model.movies

/**
 * Trakt.tv standard movie object with extended info.
 */
data class TKMovieResponse(

    val title: String,
    val year: Int?,
    val ids: TKMovieIds
)