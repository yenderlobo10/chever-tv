package io.chever.data.api.trakttv.mapper.show

import io.chever.data.api.trakttv.model.show.TKShowsAnticipatedResponse
import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.model.media.MediaItem
import io.chever.domain.model.media.MediaItemIds

fun List<TKShowsAnticipatedResponse>.mapToMediaItemList() =
    this.map { it.mapToMediaItem() }

fun TKShowsAnticipatedResponse.mapToMediaItem() = MediaItem(
    id = this.show.ids.tmdb,
    title = this.show.title,
    type = MediaTypeEnum.Show,
    ids = MediaItemIds(
        tmdb = this.show.ids.tmdb,
        traktv = this.show.ids.trakt,
        slug = this.show.ids.slug,
        imdb = this.show.ids.imdb
    ),
    metadata = mapOf(
        "listCount" to this.listCount
    )
)
