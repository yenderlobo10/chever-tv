package io.chever.data.transform

import io.chever.data.api.themoviedb.enums.TMMediaTypeEnum
import io.chever.data.api.themoviedb.model.TMTrendingResponse
import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.model.collection.MediaItem

/**
 * Transform a **List<[TMTrendingResponse]>** into **List<[MediaItem]>**.
 */
fun List<TMTrendingResponse>.mapToMediaItemList(): List<MediaItem> =
    this.map { x ->
        MediaItem(
            id = x.id,
            title = x.title ?: x.name ?: "",
            type = x.mediaType.mapToMediaTypeEnum()
        )
    }

/**
 * Transform a enum **[TMMediaTypeEnum]** into **[MediaTypeEnum]** enum.
 */
fun TMMediaTypeEnum.mapToMediaTypeEnum() = when (this) {
    TMMediaTypeEnum.Movie -> MediaTypeEnum.Movie
    TMMediaTypeEnum.Tv -> MediaTypeEnum.Show
    TMMediaTypeEnum.Person -> MediaTypeEnum.Person
    TMMediaTypeEnum.All -> MediaTypeEnum.All
}