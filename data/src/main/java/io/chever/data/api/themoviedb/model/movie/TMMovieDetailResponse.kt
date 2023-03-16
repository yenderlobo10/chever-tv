package io.chever.data.api.themoviedb.model.movie

import com.squareup.moshi.Json
import io.chever.data.api.themoviedb.enums.TMMovieStatusEnum
import io.chever.data.api.themoviedb.model.detail.TMCompany
import io.chever.data.api.themoviedb.model.detail.TMCountry
import io.chever.data.api.themoviedb.model.detail.TMGenre
import io.chever.data.api.themoviedb.model.detail.TMSpokenLanguage
import java.util.Date

/**
 * TheMovieDB standard movie details object.
 */
data class TMMovieDetailResponse(

    val adult: Boolean = false,

    @Json(name = "backdrop_path")
    val backdropPath: String?,

    val budget: Double = 0.0,
    val genres: List<TMGenre>,
    val homepage: String?,
    val id: Long,

    @Json(name = "imdb_id")
    val imdbId: String?,

    @Json(name = "original_language")
    val originalLanguage: String = "",

    @Json(name = "original_title")
    val originalTitle: String = "",

    val overview: String?,
    val popularity: Float = 0f,

    @Json(name = "poster_path")
    val posterPath: String?,

    @Json(name = "production_companies")
    val productionCompanies: List<TMCompany>,

    @Json(name = "production_countries")
    val productionCountries: List<TMCountry>,

    @Json(name = "release_date")
    val releaseDate: Date?,

    val revenue: Long = 0L,
    val runtime: Int?,

    @Json(name = "spoken_languages")
    val spokenLanguages: List<TMSpokenLanguage>,

    val status: TMMovieStatusEnum = TMMovieStatusEnum.Unknown,
    val tagline: String?,
    val title: String = "",
    val video: Boolean = false,

    @Json(name = "vote_average")
    val voteAverage: Float = 0f,

    @Json(name = "vote_count")
    val voteCount: Long = 0L,

    @Json(name = "releases")
    val certifications: TMMovieCertificationsResponse?
)