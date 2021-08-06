package io.chever.tv.ui.common.models

import java.util.*

/**
 * TODO: document class
 */
data class RelatedCardItem(

    val id: Long,
    val title: String,
    val posterPath: String?,
    val releasedDate: Date?,
    val voteAverage: Float
)
