package io.chever.data.api.trakttv.enums

import com.squareup.moshi.Json

/**
 * Trakt.tv filter time periods options.
 */
@Suppress("unused")
enum class TKPeriodEnum(
    val value: String
) {

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