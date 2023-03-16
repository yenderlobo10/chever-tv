package io.chever.data.api.themoviedb.model.detail

import com.squareup.moshi.Json

/**
 * TheMovieDB standard country object.
 */
data class TMCountry(

    @Json(name = "iso_3166_1")
    val iso: String,

    val name: String
)