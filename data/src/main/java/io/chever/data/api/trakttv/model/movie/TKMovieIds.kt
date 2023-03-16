package io.chever.data.api.trakttv.model.movie

/**
 * Trakt.tv movie object ids.
 */
data class TKMovieIds(

    val trakt: Long,
    val slug: String,
    val imdb: String,
    val tmdb: Long,
)