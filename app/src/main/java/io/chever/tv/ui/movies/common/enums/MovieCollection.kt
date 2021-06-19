package io.chever.tv.ui.movies.common.enums

import io.chever.tv.R

/**
 * This enum represent each items on [R.raw.movies_browse_collections].
 *
 * NOTE:
 * All new added items should be created here.
 *
 * @param id Same value on {R.raw.movies_list_rows@id}
 */
enum class MovieCollection(val id: Long) {

    Trending(1),

    Recommended(2),

    Played(3),

    Collected(4),

    Popular(5),

    Anticipated(6),

    // ...
}