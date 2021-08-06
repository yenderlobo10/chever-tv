package io.chever.tv.api.themoviedb.domain.models

import com.squareup.moshi.Json
import io.chever.tv.api.themoviedb.domain.enums.TMVideoType
import java.io.Serializable

/**
 * TheMovieDB item video result object.
 */
data class TMVideoResult(

    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: TMVideoType,

    @Json(name = "iso_639_1")
    val iso1: String,

    @Json(name = "iso_3166_1")
    val iso2: String,

    ) : Serializable