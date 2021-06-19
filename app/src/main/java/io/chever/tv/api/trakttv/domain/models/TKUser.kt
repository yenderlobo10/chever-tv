package io.chever.tv.api.trakttv.domain.models

import com.squareup.moshi.Json


/**
 * Trakt.tv standard user object.
 */
data class TKUser(

    val username: String,
    val private: Boolean,
    val name: String,
    val vip: Boolean,

    @Json(name = "vip_ep")
    val vipEp: Boolean,

    val ids: TKUserIds,
)
