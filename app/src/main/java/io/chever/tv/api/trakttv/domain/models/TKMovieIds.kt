package io.chever.tv.api.trakttv.domain.models

/**
 * Trakt.tv movie object ids.
 */
data class TKMovieIds(

    val trakt: Long,
    val slug: String,
    val imdb: String,
    val tmdb: Long,
)