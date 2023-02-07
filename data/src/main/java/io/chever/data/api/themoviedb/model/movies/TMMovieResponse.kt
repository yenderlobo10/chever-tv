package io.chever.data.api.themoviedb.model.movies

import com.squareup.moshi.Json
import java.io.Serializable
import java.util.Date

/**
 * TheMovieDB standard movie object.
 */
data class TMMovieResponse(

    val id: Long,
    val adult: Boolean,
    val popularity: Float,
    val title: String,
    val video: Boolean,
    val overview: String?,

    @Json(name = "backdrop_path")
    val backdropPath: String?,

    @Json(name = "original_language")
    val originalLanguage: String,

    @Json(name = "original_title")
    val originalTitle: String,

    @Json(name = "release_date")
    val releaseDate: Date?,

    @Json(name = "poster_path")
    val posterPath: String?,

    @Json(name = "vote_average")
    val voteAverage: Float,

    @Json(name = "vote_count")
    val voteCount: Long,

    @Json(name = "genre_ids")
    val genreIds: List<Int>

) : Serializable