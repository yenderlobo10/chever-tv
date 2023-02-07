package io.chever.data.transform

import io.chever.data.api.themoviedb.model.movies.TMMovieCertificationsResponse
import io.chever.data.api.themoviedb.model.movies.TMMovieDetailResponse
import io.chever.data.api.trakttv.model.movies.TKMovieResponse
import io.chever.data.api.trakttv.model.movies.TKMoviesAnticipatedResponse
import io.chever.data.api.trakttv.model.movies.TKMoviesCollectionResponse
import io.chever.data.api.trakttv.model.movies.TKMoviesRecommendedResponse
import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.model.collection.MediaItem
import io.chever.domain.model.collection.MediaItemIds
import io.chever.domain.model.movie.MovieDetail
import io.chever.shared.common.defaultCertificationCountry
import io.chever.shared.extension.toFormat
import io.chever.shared.observability.AppLogger

@JvmName("mapToMediaItemListTKMoviesCollectionResponse")
fun List<TKMoviesCollectionResponse>.mapToMediaItemList(): List<MediaItem> =
    this.map { x ->
        MediaItem(
            id = x.movie.ids.tmdb,
            title = x.movie.title,
            type = MediaTypeEnum.Movie,
            ids = MediaItemIds(
                tmdb = x.movie.ids.tmdb,
                traktv = x.movie.ids.trakt,
                slug = x.movie.ids.slug,
                imdb = x.movie.ids.imdb
            ),
            metadata = mapOf(
                "watchers" to x.watcherCount.toFormat(),
                "plays" to x.playCount.toFormat(),
                "collected" to x.collectedCount.toFormat()
            )
        )
    }

@JvmName("mapToMediaItemListTKMoviesRecommendedResponse")
fun List<TKMoviesRecommendedResponse>.mapToMediaItemList(): List<MediaItem> =
    this.map { x ->
        MediaItem(
            id = x.movie.ids.tmdb,
            title = x.movie.title,
            type = MediaTypeEnum.Movie,
            ids = MediaItemIds(
                tmdb = x.movie.ids.tmdb,
                traktv = x.movie.ids.trakt,
                slug = x.movie.ids.slug,
                imdb = x.movie.ids.imdb
            ),
            metadata = mapOf(
                "userCount" to x.userCount.toFormat()
            )
        )
    }

@JvmName("mapToMediaItemListTKMoviesAnticipatedResponse")
fun List<TKMoviesAnticipatedResponse>.mapToMediaItemList(): List<MediaItem> =
    this.map { x ->
        MediaItem(
            id = x.movie.ids.tmdb,
            title = x.movie.title,
            type = MediaTypeEnum.Movie,
            ids = MediaItemIds(
                tmdb = x.movie.ids.tmdb,
                traktv = x.movie.ids.trakt,
                slug = x.movie.ids.slug,
                imdb = x.movie.ids.imdb
            ),
            metadata = mapOf(
                "listCount" to x.listCount.toFormat()
            )
        )
    }

@JvmName("mapToMediaItemListTKMovieResponse")
fun List<TKMovieResponse>.mapToMediaItemList(): List<MediaItem> =
    this.map { x ->
        MediaItem(
            id = x.ids.tmdb,
            title = x.title,
            type = MediaTypeEnum.Movie,
            ids = MediaItemIds(
                tmdb = x.ids.tmdb,
                traktv = x.ids.trakt,
                slug = x.ids.slug,
                imdb = x.ids.imdb
            )
        )
    }

fun TMMovieDetailResponse.mapToMovieDetail() = MovieDetail(
    id = this.id,
    title = this.title,
    overview = this.overview ?: "",
    posterPath = this.posterPath ?: "",
    backdropPath = this.backdropPath ?: "",
    genres = this.genres.map { g -> g.name },
    releaseAt = this.releaseDate,
    runtime = this.runtime ?: 0,
    homepage = this.homepage ?: "",
    isAdult = this.adult,
    originalLanguage = this.originalLanguage,
    originalTitle = this.originalTitle,
    popularity = this.popularity,
    voteAverage = this.voteAverage,
    voteCount = this.voteCount,
    budget = this.budget,
    revenue = this.revenue,
    status = this.status.value,
    tagline = this.tagline ?: "",
    hasVideo = this.video,
    certification = this.certifications.getDefault()
)

private fun TMMovieCertificationsResponse?.getDefault(): String? = try {

    this?.countries
        ?.filter { x -> x.iso == defaultCertificationCountry }
        ?.sortedByDescending { x -> x.releaseDate }
        ?.first()
        ?.certification

} catch (ex: Exception) {
    AppLogger.warning(ex)
    null
}
