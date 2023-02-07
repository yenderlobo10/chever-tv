package io.chever.data.api.trakttv.model.shows

import com.squareup.moshi.Json

/**
 * Trakt.tv shows watched object response.
 */
data class TKShowsCollectionResponse(

    @Json(name = "watcher_count")
    val watcherCount: Int,

    @Json(name = "play_count")
    val playCount: Int,

    @Json(name = "collected_count")
    val collectedCount: Int,

    @Json(name = "collector_count")
    val collectorCount: Int,

    val show: TKShowResponse
)
