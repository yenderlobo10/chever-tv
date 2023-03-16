package io.chever.data.api.themoviedb.model.show

import com.squareup.moshi.Json
import java.util.Date

/**
 * TheMovieDB standard movie object.
 */
data class TMShowResponse(

    val id: Long,
    val adult: Boolean = false,
    val popularity: Float = 0f,
    val name: String,
    val overview: String?,

    @Json(name = "backdrop_path")
    val backdropPath: String?,

    @Json(name = "original_language")
    val originalLanguage: String?,

    @Json(name = "original_name")
    val originalName: String?,

    @Json(name = "first_air_date")
    val firstAirDate: Date?,

    @Json(name = "poster_path")
    val posterPath: String?,

    @Json(name = "vote_average")
    val voteAverage: Float = 0f,

    @Json(name = "vote_count")
    val voteCount: Long = 0L,

    @Json(name = "genre_ids")
    val genreIds: List<Int>
)