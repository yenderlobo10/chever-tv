package io.chever.data.api.themoviedb.mapper.show

import io.chever.data.api.themoviedb.model.TMCollectionResponse
import io.chever.data.api.themoviedb.model.show.TMShowResponse
import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.model.media.MediaItem

fun TMCollectionResponse<TMShowResponse>.mapToMediaItemList() =
    this.results.map {
        it.mapToMediaItem()
    }

fun TMShowResponse.mapToMediaItem() = MediaItem(
    id = this.id,
    title = this.name,
    type = MediaTypeEnum.Show
)