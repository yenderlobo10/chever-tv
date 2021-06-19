package io.chever.tv.api.themoviedb.domain.models

import com.squareup.moshi.Json

/**
 * TheMovieDB standard spoken-language object.
 */
data class TMSpokenLanguage(

    @Json(name = "iso_639_1")
    val iso: String,

    val name: String,
)
