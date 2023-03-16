package io.chever.data.api.themoviedb.model.detail

import com.squareup.moshi.Json

/**
 * TheMovieDB standard company object.
 */
data class TMCompany(

    val id: Long,
    val name: String,

    @Json(name = "logo_path")
    val logoPath: String?,

    @Json(name = "origin_country")
    val originCountry: String
)