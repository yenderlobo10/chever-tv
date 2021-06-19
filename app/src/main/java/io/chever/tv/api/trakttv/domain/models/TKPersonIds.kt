package io.chever.tv.api.trakttv.domain.models

/**
 * Trakt.tv person object ids.
 */
data class TKPersonIds(

    val trakt: Long,
    val slug: String,
    val imdb: String,
    val tmdb: Long,
)