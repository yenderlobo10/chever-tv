package io.chever.domain.enums

@Suppress("unused")
enum class TimeWindowEnum(
    val value: String,
    val period: String
) {

    Day(
        value = "day",
        period = "daily"
    ),

    Week(
        value = "week",
        period = "weekly"
    ),

    Month(
        value = "month",
        period = "monthly"
    )
}