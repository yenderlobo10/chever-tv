package io.chever.tv.api.trakttv.domain.enums

import com.squareup.moshi.Json

/**
 * Trakt.tv sorting options.
 */
enum class TKSort(val value: String) {

    // Default
    @Json(name = "newest")
    Newest("newest"),

    @Json(name = "oldest")
    Oldest("oldest"),

    @Json(name = "likes")
    Likes("likes"),

    @Json(name = "replies")
    Replies("replies"),

    @Json(name = "highest")
    Highest("highest"),

    @Json(name = "lowest")
    Lowest("lowest"),

    @Json(name = "plays")
    Plays("plays"),
}