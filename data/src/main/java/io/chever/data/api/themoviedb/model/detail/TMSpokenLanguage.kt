package io.chever.data.api.themoviedb.model.detail

import com.squareup.moshi.Json

/**
 * TheMovieDB standard spoken-language object.
 */
data class TMSpokenLanguage(

    @Json(name = "iso_639_1")
    val iso: String,

    val name: String
)