package io.chever.tv.api.trakttv.domain.models

/**
 * Trakt.tv episode object ids.
 */
data class TKEpisodeIds(

    val trakt: Long,
    val tvdb: Long,
    val imdb: String,
    val tmdb: Long,
)