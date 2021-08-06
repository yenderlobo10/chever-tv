package io.chever.tv.api.themoviedb.domain.models

import com.squareup.moshi.Json
import java.io.Serializable

/**
 * TheMovieDB standard country object.
 */
data class TMCountry(

    @Json(name = "iso_3166_1")
    val iso: String,

    val name: String,

    ) : Serializable