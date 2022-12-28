package io.chever.apptv.ui.home.enums

import androidx.annotation.StringRes
import io.chever.apptv.R

/**
 * Home list collections to browse.
 */
enum class HomeCollection(
    val id: Long,
    @StringRes val resTitleId: Int
) {

    /**
     * [TMDB]
     * List the daily trending media items.
     */
    TrendingDay(
        id = 1,
        resTitleId = R.string.home_browse_row_trending_day_title
    ),

    /**
     * [TMDB]
     * List the weekly trending media items.
     */
    TrendingWeek(
        id = 2,
        resTitleId = R.string.home_browse_row_trending_week_title
    ),

    /**
     * List the most watched media items.
     */
    Watched(
        id = 3,
        resTitleId = R.string.home_browse_row_watched_title
    ),

    /**
     * List the most recommended media items.
     */
    Recommended(
        id = 4,
        resTitleId = R.string.home_browse_row_recommended_title
    ),

    /**
     * List the most collected media items.
     */
    Collected(
        id = 5,
        resTitleId = R.string.home_browse_row_collected_title
    ),

    /**
     * List the most popular media items.
     */
    Popular(
        id = 6,
        resTitleId = R.string.home_browse_row_popular_title
    ),

    /**
     * List the most anticipated media items.
     */
    Anticipated(
        id = 7,
        resTitleId = R.string.home_browse_row_anticipated_title
    )
}