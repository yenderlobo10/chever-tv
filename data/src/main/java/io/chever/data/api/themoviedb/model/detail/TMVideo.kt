package io.chever.data.api.themoviedb.model.detail

import com.squareup.moshi.Json
import io.chever.data.api.themoviedb.enums.TMVideoTypeEnum
import java.util.Date

/**
 * TheMovieDB item video result object.
 */
data class TMVideo(

    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: TMVideoTypeEnum,

    @Json(name = "official")
    val isOfficial: Boolean,

    @Json(name = "published_at")
    val publishedAt: Date?,

    @Json(name = "iso_639_1")
    val iso1: String,

    @Json(name = "iso_3166_1")
    val iso2: String
)