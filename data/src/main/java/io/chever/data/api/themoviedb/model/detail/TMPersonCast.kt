package io.chever.data.api.themoviedb.model.detail

import com.squareup.moshi.Json

/**
 * TheMovieDB people cast object.
 */
data class TMPersonCast(

    val id: Long,
    val name: String,
    val adult: Boolean = false,
    val gender: Int = 0,
    val popularity: Float = 0f,
    val character: String,
    val order: Int,

    @Json(name = "known_for_department")
    val knownForDepartment: String?,

    @Json(name = "profile_path")
    val profilePath: String?,

    @Json(name = "original_name")
    val originalName: String?,

    @Json(name = "cast_id")
    val castId: Int?,

    @Json(name = "credit_id")
    val creditId: String?
)