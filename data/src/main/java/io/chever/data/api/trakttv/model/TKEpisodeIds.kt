package io.chever.data.api.trakttv.model

/**
 * Trakt.tv episode object ids.
 */
data class TKEpisodeIds(

    val trakt: Long,
    val tvdb: Long,
    val imdb: String,
    val tmdb: Long,
)