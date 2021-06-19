package io.chever.tv.api.trakttv.domain.enums

import com.squareup.moshi.Json

/**
 * Trakt.tv filter time periods options.
 */
enum class TKPeriod(val value: String) {

    @Json(name = "daily")
    Daily("daily"),

    // Default
    @Json(name = "weekly")
    Weekly("weekly"),

    @Json(name = "monthly")
    Monthly("monthly"),

    @Json(name = "yearly")
    Yearly("yearly"),

    @Json(name = "all")
    All("all"),
}