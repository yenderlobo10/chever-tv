package io.chever.data.api.trakttv.model.movies

/**
 * Trakt.tv movies trending object response.
 */
data class TKMoviesTrendingResponse(

    val watchers: Int,
    val movie: TKMovieResponse,
)