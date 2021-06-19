package io.chever.tv.api.trakttv.domain.models

import com.squareup.moshi.Json

/**
 * Trakt.tv movies played object.
 */
data class TKMoviePlayed(

    @Json(name = "watcher_count")
    val watcherCount: Int,

    @Json(name = "play_count")
    val playCount: Int,

    @Json(name = "collected_count")
    val collectedCount: Int,

    val movie: TKMovie,
)