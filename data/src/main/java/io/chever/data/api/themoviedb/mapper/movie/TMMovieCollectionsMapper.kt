package io.chever.data.api.themoviedb.mapper.movie

import io.chever.data.api.themoviedb.model.TMCollectionResponse
import io.chever.data.api.themoviedb.model.movie.TMMovieResponse
import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.model.media.MediaItem

fun TMCollectionResponse<TMMovieResponse>.mapToMediaItemList() =
    this.results.map {
        it.mapToMediaItem()
    }

fun TMMovieResponse.mapToMediaItem() = MediaItem(
    id = this.id,
    title = this.title,
    type = MediaTypeEnum.Movie
)