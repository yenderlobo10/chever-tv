package io.chever.domain.model.media

import io.chever.domain.enums.MediaTypeEnum
import java.io.Serializable

/**
 * Model to represent a media item like movie, show or person.
 *
 * @param id Id of media item.
 * @param title Title or name of media item.
 * @param type Type of media item. Must be one of [MediaTypeEnum].
 * @param ids Provider media ids.
 * @param detail Detail of media item.
 * @param metadata Media item extra data.
 */
data class MediaItem(

    val id: Long,
    val title: String,
    val type: MediaTypeEnum,
    val ids: MediaItemIds = MediaItemIds(),
    val detail: MediaItemDetail = MediaItemDetail.None,
    val metadata: Map<String, Any> = emptyMap()

) : Serializable
