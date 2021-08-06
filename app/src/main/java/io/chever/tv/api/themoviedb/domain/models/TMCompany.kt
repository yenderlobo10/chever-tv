package io.chever.tv.api.themoviedb.domain.models

import com.squareup.moshi.Json
import java.io.Serializable

/**
 * TheMovieDB standard company object.
 */
data class TMCompany(

    val id: Long,
    val name: String,

    @Json(name = "logo_path")
    val logoPath: String?,

    @Json(name = "origin_country")
    val originCountry: String,

    ) : Serializable