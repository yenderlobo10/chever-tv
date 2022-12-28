package io.chever.data.api.themoviedb.enums

import com.squareup.moshi.Json

/**
 * Valid time windows values. Common use in Trending.
 */
enum class TMTimeWindowEnum(
    val value: String
) {

    /**
     * View the trending list for the day.
     */
    @Json(name = "day")
    Day(value = "day"),

    /**
     * View the trending list for the week.
     */
    @Json(name = "week")
    Week(value = "week")
}