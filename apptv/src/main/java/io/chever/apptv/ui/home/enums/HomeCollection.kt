package io.chever.apptv.ui.home.enums

import androidx.annotation.StringRes
import io.chever.apptv.R
import io.chever.apptv.ui.home.model.MediaItemMetadata

/**
 * Home list collections to browse.
 *
 * @param id
 * @param resTitleId
 * @param metadata
 */
enum class HomeCollection(
    val id: Long,
    @StringRes val resTitleId: Int,
    val metadata: List<MediaItemMetadata> = emptyList()
) {

    /**
     * List the daily trending media items.
     */
    TrendingDay(
        id = 1,
        resTitleId = R.string.home_browse_row_trending_day_title
    ),

    /**
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
        resTitleId = R.string.home_browse_row_watched_title,
        metadata = listOf(
            MediaItemMetadata(
                keyValue = "watchers",
                resColor = R.color.labelRed,
                resLabel = R.string.metadata_label_watchers
            ),
            MediaItemMetadata(
                keyValue = "plays",
                resColor = R.color.labelGray,
                resLabel = R.string.metadata_label_plays
            )
        )
    ),

    /**
     * List the most recommended media items.
     */
    Recommended(
        id = 4,
        resTitleId = R.string.home_browse_row_recommended_title,
        metadata = listOf(
            MediaItemMetadata(
                keyValue = "userCount",
                resColor = R.color.labelOrange,
                resLabel = R.string.metadata_label_recommended
            )
        )
    ),

    /**
     * List the most collected media items.
     */
    Collected(
        id = 5,
        resTitleId = R.string.home_browse_row_collected_title,
        metadata = listOf(
            MediaItemMetadata(
                keyValue = "collected",
                resColor = R.color.labelTeal,
                resLabel = R.string.metadata_label_collected
            ),
            MediaItemMetadata(
                keyValue = "collector",
                resColor = R.color.labelGray,
                resLabel = R.string.metadata_label_collector
            )
        )
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
        resTitleId = R.string.home_browse_row_anticipated_title,
        metadata = listOf(
            MediaItemMetadata(
                keyValue = "listCount",
                resColor = R.color.labelBlue,
                resLabel = R.string.metadata_label_list
            )
        )
    )
}