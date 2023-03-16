package io.chever.data.api.themoviedb.mapper.movie

import io.chever.data.api.themoviedb.model.movie.TMMovieCertificationsResponse
import io.chever.data.api.themoviedb.model.movie.TMMovieDetailResponse
import io.chever.domain.model.movie.MovieDetail
import io.chever.shared.common.defaultCertificationCountry
import io.chever.shared.observability.AppLogger

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