package io.chever.data.api.trakttv.enums

import com.squareup.moshi.Json

/**
 * Trakt.tv allow options to use in **extended** query parameter.
 *
 * *This returns a lot of extra data, so please only use extended parameters
 * if you actually need them!*
 */
@Suppress("unused")
enum class TKExtendedInfo(
    val value: String
) {

    /** Complete info for an item. */
    @Json(name = "full")
    Full("full"),

    /** Collection only. Additional video and audio info. */
    @Json(name = "metadata")
    MetaData("metadata")
}