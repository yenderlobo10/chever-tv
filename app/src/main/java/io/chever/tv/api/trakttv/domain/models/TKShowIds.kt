package io.chever.tv.api.trakttv.domain.models

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