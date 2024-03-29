package io.chever.tv.api.themoviedb.domain.models

import com.squareup.moshi.Json
import java.io.Serializable

/**
 * TheMovieDB standard object alternative title for a movie.
 */
data class TMMovieTitle(

    @Json(name = "iso_3166_1")
    val country: String,

    val title: String,

    ) : Serializable
