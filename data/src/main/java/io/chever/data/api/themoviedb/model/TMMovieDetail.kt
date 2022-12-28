package io.chever.data.api.themoviedb.model

import com.squareup.moshi.Json
import io.chever.data.api.themoviedb.enums.TMMovieStatusEnum
import java.io.Serializable
import java.util.Date

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

    val status: TMMovieStatusEnum,
    val tagline: String?,
    val title: String,
    val video: Boolean,

    @Json(name = "vote_average")
    val voteAverage: Float,

    @Json(name = "vote_count")
    val voteCount: Long

) : Serializable