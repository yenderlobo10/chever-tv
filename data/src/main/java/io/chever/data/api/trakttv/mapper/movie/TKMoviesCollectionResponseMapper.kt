package io.chever.data.api.trakttv.mapper.movie

import io.chever.data.api.trakttv.model.movie.TKMoviesCollectionResponse
import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.model.media.MediaItem
import io.chever.domain.model.media.MediaItemIds
import io.chever.shared.extension.toFormat

fun List<TKMoviesCollectionResponse>.mapToMediaItemList(): List<MediaItem> =
    this.map { it.mapToMediaItem() }

fun TKMoviesCollectionResponse.mapToMediaItem() = MediaItem(
    id = this.movie.ids.tmdb,
    title = this.movie.title,
    type = MediaTypeEnum.Movie,
    ids = MediaItemIds(
        tmdb = this.movie.ids.tmdb,
        traktv = this.movie.ids.trakt,
        slug = this.movie.ids.slug,
        imdb = this.movie.ids.imdb
    ),
    metadata = mapOf(
        "watchers" to this.watcherCount.toFormat(),
        "plays" to this.playCount.toFormat(),
        "collected" to this.collectedCount.toFormat()
    )
)