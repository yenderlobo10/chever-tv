package io.chever.data.api.trakttv.model

import com.squareup.moshi.Json

/**
 * Trakt.tv user object stats.
 */
data class TKUserStats(

    val rating: Float,

    @Json(name = "play_count")
    val playCount: Int,

    @Json(name = "completed_count")
    val completedCount: Int,
)