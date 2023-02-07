package io.chever.data.api.trakttv.model.shows

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