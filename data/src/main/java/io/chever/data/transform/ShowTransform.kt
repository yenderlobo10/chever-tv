package io.chever.data.transform

import io.chever.data.api.themoviedb.model.TMResultsResponse
import io.chever.data.api.themoviedb.model.shows.TMEpisode
import io.chever.data.api.themoviedb.model.shows.TMSeason
import io.chever.data.api.themoviedb.model.shows.TMShowCertification
import io.chever.data.api.themoviedb.model.shows.TMShowDetailResponse
import io.chever.data.api.trakttv.model.shows.TKShowResponse
import io.chever.data.api.trakttv.model.shows.TKShowsAnticipatedResponse
import io.chever.data.api.trakttv.model.shows.TKShowsCollectionResponse
import io.chever.data.api.trakttv.model.shows.TKShowsRecommendedResponse
import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.model.collection.MediaItem
import io.chever.domain.model.collection.MediaItemIds
import io.chever.domain.model.show.ShowDetail
import io.chever.domain.model.show.ShowEpisode
import io.chever.domain.model.show.ShowSeason
import io.chever.shared.common.defaultCertificationCountry
import io.chever.shared.extension.calculateAverage
import io.chever.shared.extension.toFormat
import io.chever.shared.observability.AppLogger

/**
 * TODO: document ext function
 */
@JvmName("mapToMediaItemListTKShowsCollectionResponse")
fun List<TKShowsCollectionResponse>.mapToMediaItemList() = this.map {
    with(it.show) {
        MediaItem(
            id = this.ids.tmdb,
            title = this.title,
            type = MediaTypeEnum.Show,
            ids = MediaItemIds(
                tmdb = this.ids.tmdb,
                traktv = this.ids.trakt,
                slug = this.ids.slug,
                imdb = this.ids.imdb
            ),
            metadata = mapOf(
                "watchers" to it.watcherCount.toFormat(),
                "plays" to it.playCount.toFormat(),
                "collected" to it.collectedCount.toFormat(),
                "collector" to it.collectorCount.toFormat()
            )
        )
    }
}

/**
 * TODO: document ext function
 */
@JvmName("mapToMediaItemListTKShowsRecommendedResponse")
fun List<TKShowsRecommendedResponse>.mapToMediaItemList() = this.map {
    with(it.show) {
        MediaItem(
            id = this.ids.tmdb,
            title = this.title,
            type = MediaTypeEnum.Show,
            ids = MediaItemIds(
                tmdb = this.ids.tmdb,
                traktv = this.ids.trakt,
                slug = this.ids.slug,
                imdb = this.ids.imdb
            ),
            metadata = mapOf(
                "userCount" to it.userCount
            )
        )
    }
}

/**
 * TODO: document ext function
 */
@JvmName("mapToMediaItemListTKShowsAnticipatedResponse")
fun List<TKShowsAnticipatedResponse>.mapToMediaItemList() = this.map {
    with(it.show) {
        MediaItem(
            id = this.ids.tmdb,
            title = this.title,
            type = MediaTypeEnum.Show,
            ids = MediaItemIds(
                tmdb = this.ids.tmdb,
                traktv = this.ids.trakt,
                slug = this.ids.slug,
                imdb = this.ids.imdb
            ),
            metadata = mapOf(
                "listCount" to it.listCount
            )
        )
    }
}

/**
 * TODO: document ext function
 */
@JvmName("mapToMediaItemListTKShowResponse")
fun List<TKShowResponse>.mapToMediaItemList() = this.map {
    MediaItem(
        id = it.ids.tmdb,
        title = it.title,
        type = MediaTypeEnum.Show,
        ids = MediaItemIds(
            tmdb = it.ids.tmdb,
            traktv = it.ids.trakt,
            slug = it.ids.slug,
            imdb = it.ids.imdb
        )
    )
}

/**
 * TODO: document ext function
 */
fun TMShowDetailResponse.mapToShowDetail() = ShowDetail(
    id = this.id,
    name = this.name,
    overview = this.overview ?: "",
    posterPath = this.posterPath ?: "",
    backdropPath = this.backdropPath ?: "",
    genres = this.genres.map { g -> g.name },
    firstAirDate = this.firstAirDate,
    createdBy = this.createdBy.map { c -> c.name },
    seasons = this.seasons.map { s -> s.mapToShowSeason() },
    episodeRunTimeAverage = this.episodeRunTime.calculateAverage(),
    lastEpisodeToAir = this.lastEpisodeToAir?.mapToShowEpisode(),
    numberOfEpisodes = this.numberOfEpisodes,
    numberOfSeasons = this.numberOfSeasons,
    lastAirDate = this.lastAirDate,
    homepage = this.homepage ?: "",
    isAdult = this.adult,
    originalLanguage = this.originalLanguage ?: "",
    originalName = this.originalName,
    popularity = this.popularity,
    voteAverage = this.voteAverage,
    voteCount = this.voteCount,
    status = this.status ?: "",
    tagline = this.tagline ?: "",
    inProduction = this.inProduction,
    certification = this.certifications.getDefault()
)

/**
 * TODO: document ext function
 */
fun TMSeason.mapToShowSeason() = ShowSeason(
    id = this.id,
    name = this.name,
    overview = this.overview ?: "",
    posterPath = this.posterPath ?: "",
    airDate = this.airDate,
    episodeCount = this.episodeCount,
    number = this.seasonNumber
)

/**
 * TODO: document ext function
 */
fun TMEpisode.mapToShowEpisode() = ShowEpisode(
    id = this.id,
    name = this.name ?: "",
    overview = this.overview ?: "",
    airDate = this.airDate,
    stillPath = this.stillPath,
    showId = this.showId,
    seasonNumber = this.seasonNumber,
    number = this.episodeNumber ?: 0,
    runtime = this.runtime,
    voteAverage = this.voteAverage,
    voteCount = this.voteCount,
)


private fun TMResultsResponse<TMShowCertification>?.getDefault(): String? = try {

    this?.results
        ?.first { x -> x.iso == defaultCertificationCountry }
        ?.rating

} catch (ex: Exception) {
    AppLogger.warning(ex)
    null
}