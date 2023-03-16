package io.chever.data.api.trakttv.mapper.movie

import io.chever.data.api.trakttv.model.movie.TKMovieResponse
import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.model.media.MediaItem
import io.chever.domain.model.media.MediaItemIds

fun List<TKMovieResponse>.mapToMediaItemList(): List<MediaItem> =
    this.map { it.mapToMediaItem() }

fun TKMovieResponse.mapToMediaItem() = MediaItem(
    id = this.ids.tmdb,
    title = this.title,
    type = MediaTypeEnum.Movie,
    ids = MediaItemIds(
        tmdb = this.ids.tmdb,
        traktv = this.ids.trakt,
        slug = this.ids.slug,
        imdb = this.ids.imdb
    )
)