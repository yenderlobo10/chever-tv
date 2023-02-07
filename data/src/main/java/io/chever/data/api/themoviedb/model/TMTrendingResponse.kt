package io.chever.data.api.themoviedb.model

import com.squareup.moshi.Json
import io.chever.data.api.themoviedb.enums.TMMediaTypeEnum
import java.io.Serializable
import java.util.Date

data class TMTrendingResponse(

    val id: Long,
    val adult: Boolean = false,
    val popularity: Float = 0f,
    val title: String?,
    val name: String?,
    val video: Boolean = false,
    val overview: String?,

    @Json(name = "media_type")
    val mediaType: TMMediaTypeEnum,

    @Json(name = "backdrop_path")
    val backdropPath: String?,

    @Json(name = "original_language")
    val originalLanguage: String?,

    @Json(name = "original_title")
    val originalTitle: String?,

    @Json(name = "release_date")
    val releaseDate: Date?,

    @Json(name = "first_air_date")
    val firstAirDate: Date?,

    @Json(name = "poster_path")
    val posterPath: String?,

    @Json(name = "vote_average")
    val voteAverage: Float = 0f,

    @Json(name = "vote_count")
    val voteCount: Long = 0L,

    @Json(name = "genre_ids")
    val genreIds: List<Int>?

) : Serializable