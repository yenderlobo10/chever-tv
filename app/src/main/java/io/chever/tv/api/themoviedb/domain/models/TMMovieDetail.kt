package io.chever.tv.api.themoviedb.domain.models

import com.squareup.moshi.Json
import io.chever.tv.api.themoviedb.domain.enums.TMMovieStatus
import java.util.*

/**
 * TheMovieDB standard movie details object.
 */
data class TMMovieDetail(

    val adult: Boolean,

    @Json(name = "backdrop_path")
    val backdropPath: String?,

    val budget: Double,
    val genres: List<TMGenre>,
    val homepage: String?,
    val id: Long,

    @Json(name = "imdb_id")
    val imdbId: String?,

    @Json(name = "original_language")
    val originalLanguage: String,

    @Json(name = "original_title")
    val originalTitle: String,

    val overview: String?,
    val popularity: Float,

    @Json(name = "poster_path")
    val posterPath: String?,

    @Json(name = "production_companies")
    val productionCompanies: List<TMCompany>,

    @Json(name = "production_countries")
    val productionCountries: List<TMCountry>,

    @Json(name = "release_date")
    val releaseDate: Date?,

    val revenue: Long,
    val runtime: Int?,

    @Json(name = "spoken_languages")
    val spokenLanguages: List<TMSpokenLanguage>,

    val status: TMMovieStatus,
    val tagline: String?,
    val title: String,
    val video: Boolean,

    @Json(name = "vote_average")
    val voteAverage: Float,

    @Json(name = "vote_count")
    val voteCount: Long,
)
