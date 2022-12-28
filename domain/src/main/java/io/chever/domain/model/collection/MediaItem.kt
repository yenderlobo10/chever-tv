package io.chever.domain.model.collection

import io.chever.domain.enums.MediaTypeEnum
import java.util.Date

data class MediaItem(

    val id: Long,
    val title: String,
    val release: Date?,
    val posterPath: String?,
    val backdropPath: String?,
    val rating: Float,
    val type: MediaTypeEnum,
)
