package io.chever.tv.api.themoviedb.domain.models

import com.squareup.moshi.Json

/**
 * TheMovieDB people cast object.
 */
data class TMPeopleCast(

    val id: Long,
    val name: String,
    val adult: Boolean,
    val gender: Int?,
    val popularity: Float,
    val character: String,
    val order: Int,

    @Json(name = "known_for_department")
    val knownForDepartment: String,

    @Json(name = "profile_path")
    val profilePath: String?,

    @Json(name = "original_name")
    val originalName: String,

    @Json(name = "cast_id")
    val castId: Int,

    @Json(name = "credit_id")
    val creditId: String,
)
