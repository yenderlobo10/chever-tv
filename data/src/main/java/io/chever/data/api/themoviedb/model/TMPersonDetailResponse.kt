package io.chever.data.api.themoviedb.model

import com.squareup.moshi.Json
import java.io.Serializable
import java.util.Date

/**
 * TheMovieDB people detail object.
 */
data class TMPersonDetailResponse(

    val id: Long,
    val name: String,
    val adult: Boolean,
    val gender: Int?,
    val popularity: Float,
    val birthday: Date?,
    val biography: String,
    val homepage: String?,

    @Json(name = "known_for_department")
    val knownForDepartment: String,

    @Json(name = "profile_path")
    val profilePath: String?,

    @Json(name = "deathday")
    val deathDay: String?,

    @Json(name = "place_of_birth")
    val placeOfBirth: String?,

    @Json(name = "imdb_id")
    val imdbId: String,

    @Json(name = "also_known_as")
    val alsoKnownAs: List<String>

) : Serializable