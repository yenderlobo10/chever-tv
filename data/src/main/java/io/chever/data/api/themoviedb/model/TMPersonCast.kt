package io.chever.data.api.themoviedb.model

import com.squareup.moshi.Json
import java.io.Serializable

/**
 * TheMovieDB people cast object.
 */
data class TMPersonCast(

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
    val creditId: String

) : Serializable