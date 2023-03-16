package io.chever.data.api.trakttv.mapper.show

import io.chever.data.api.trakttv.model.show.TKShowsCollectionResponse
import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.model.media.MediaItem
import io.chever.domain.model.media.MediaItemIds
import io.chever.shared.extension.toFormat

fun List<TKShowsCollectionResponse>.mapToMediaItemList() =
    this.map { it.mapToMediaItem() }

fun TKShowsCollectionResponse.mapToMediaItem() = MediaItem(
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
        "watchers" to this.watcherCount.toFormat(),
        "plays" to this.playCount.toFormat(),
        "collected" to this.collectedCount.toFormat(),
        "collector" to this.collectorCount.toFormat()
    )
)
