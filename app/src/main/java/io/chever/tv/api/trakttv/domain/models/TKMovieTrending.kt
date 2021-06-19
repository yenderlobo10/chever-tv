package io.chever.tv.api.trakttv.domain.models

/**
 * Trakt.tv movies trending object.
 */
data class TKMovieTrending(

    val watchers: Int,
    val movie: TKMovie,
)
