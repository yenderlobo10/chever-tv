package io.chever.data.transform

import io.chever.data.api.themoviedb.enums.TMMediaTypeEnum
import io.chever.data.api.themoviedb.model.TMTrending
import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.model.collection.MediaItem

/**
 * Transform a **List<[TMTrending]>** into **List<[MediaItem]>**.
 */
fun List<TMTrending>.mapToMediaItemList(): List<MediaItem> =
    this.map { x ->
        MediaItem(
            id = x.id,
            title = x.title ?: x.name ?: "",
            release = x.releaseDate ?: x.firstAirDate,
            posterPath = x.posterPath,
            backdropPath = x.backdropPath,
            rating = x.voteAverage,
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