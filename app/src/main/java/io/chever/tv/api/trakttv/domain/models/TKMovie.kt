package io.chever.tv.api.trakttv.domain.models

/**
 * Trakt.tv standard movie object.
 */
data class TKMovie(

    val title: String,
    val year: Int,
    val ids: TKMovieIds,
)
