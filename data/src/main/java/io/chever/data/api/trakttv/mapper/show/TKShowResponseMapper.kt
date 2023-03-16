package io.chever.data.api.trakttv.mapper.show

import io.chever.data.api.trakttv.model.show.TKShowResponse
import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.model.media.MediaItem
import io.chever.domain.model.media.MediaItemIds

fun List<TKShowResponse>.mapToMediaItemList() =
    this.map { it.mapToMediaItem() }

fun TKShowResponse.mapToMediaItem() = MediaItem(
    id = this.ids.tmdb,
    title = this.title,
    type = MediaTypeEnum.Show,
    ids = MediaItemIds(
        tmdb = this.ids.tmdb,
        traktv = this.ids.trakt,
        slug = this.ids.slug,
        imdb = this.ids.imdb
    )
)
