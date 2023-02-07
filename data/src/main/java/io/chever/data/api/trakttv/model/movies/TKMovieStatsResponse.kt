package io.chever.data.api.trakttv.model.movies

/**
 * Trakt.tv movie stats object.
 */
data class TKMovieStatsResponse(

    val watchers: Long,
    val plays: Long,
    val collectors: Long,
    val comments: Long,
    val lists: Long,
    val votes: Long,
    val recommended: Long,
)