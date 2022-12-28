package io.chever.data.api.trakttv.model

/**
 * Trakt.tv movies trending object.
 */
data class TKMovieTrendingResult(

    val watchers: Int,
    val movie: TKMovie,
)