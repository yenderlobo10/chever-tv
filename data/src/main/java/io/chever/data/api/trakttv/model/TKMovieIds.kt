package io.chever.data.api.trakttv.model

/**
 * Trakt.tv movie object ids.
 */
data class TKMovieIds(

    val trakt: Long,
    val slug: String,
    val imdb: String,
    val tmdb: Long,
)