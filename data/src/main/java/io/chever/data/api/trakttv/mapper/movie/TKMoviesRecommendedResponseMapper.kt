package io.chever.data.api.trakttv.mapper.movie

import io.chever.data.api.trakttv.model.movie.TKMoviesRecommendedResponse
import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.model.media.MediaItem
import io.chever.domain.model.media.MediaItemIds
import io.chever.shared.extension.toFormat

fun List<TKMoviesRecommendedResponse>.mapToMediaItemList(): List<MediaItem> =
    this.map { it.mapToMediaItem() }

fun TKMoviesRecommendedResponse.mapToMediaItem() = MediaItem(
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
        "userCount" to this.userCount.toFormat()
    )
)