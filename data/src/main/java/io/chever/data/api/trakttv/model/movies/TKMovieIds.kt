package io.chever.data.api.trakttv.model.movies

/**
 * Trakt.tv movie object ids.
 */
data class TKMovieIds(

    val trakt: Long,
    val slug: String,
    val imdb: String,
    val tmdb: Long,
)