package io.chever.data.api.trakttv.model

/**
 * Trakt.tv show object ids.
 */
data class TKShowIds(

    val trakt: Long,
    val slug: String,
    val tvdb: Long,
    val imdb: String,
    val tmdb: Long,
)