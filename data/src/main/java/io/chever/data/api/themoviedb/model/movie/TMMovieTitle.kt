package io.chever.data.api.themoviedb.model.movie

import com.squareup.moshi.Json

/**
 * TheMovieDB standard object alternative title for a movie.
 */
data class TMMovieTitle(

    @Json(name = "iso_3166_1")
    val country: String,

    val title: String
)