package io.chever.data.api.themoviedb.model

import com.squareup.moshi.Json
import java.io.Serializable

/**
 * TheMovieDB standard spoken-language object.
 */
data class TMSpokenLanguage(

    @Json(name = "iso_639_1")
    val iso: String,

    val name: String

) : Serializable