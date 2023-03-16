package io.chever.data.api.themoviedb.mapper.show

import io.chever.data.api.themoviedb.model.TMResultsResponse
import io.chever.data.api.themoviedb.model.show.TMEpisode
import io.chever.data.api.themoviedb.model.show.TMSeason
import io.chever.data.api.themoviedb.model.show.TMShowCertification
import io.chever.data.api.themoviedb.model.show.TMShowDetailResponse
import io.chever.domain.model.show.ShowDetail
import io.chever.domain.model.show.ShowEpisode
import io.chever.domain.model.show.ShowSeason
import io.chever.shared.common.defaultCertificationCountry
import io.chever.shared.extension.calculateAverage
import io.chever.shared.observability.AppLogger


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

fun TMSeason.mapToShowSeason() = ShowSeason(
    id = this.id,
    name = this.name,
    overview = this.overview ?: "",
    posterPath = this.posterPath ?: "",
    airDate = this.airDate,
    episodeCount = this.episodeCount,
    number = this.seasonNumber
)

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