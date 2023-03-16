package io.chever.data.api.trakttv.model.movie

import com.squareup.moshi.Json

/**
 * Trakt.tv movies played object response.
 */
data class TKMoviesCollectionResponse(

    @Json(name = "watcher_count")
    val watcherCount: Int,

    @Json(name = "play_count")
    val playCount: Int,

    @Json(name = "collected_count")
    val collectedCount: Int,

    val movie: TKMovieResponse,
)