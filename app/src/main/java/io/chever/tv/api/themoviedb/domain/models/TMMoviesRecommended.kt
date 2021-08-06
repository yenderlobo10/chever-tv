package io.chever.tv.api.themoviedb.domain.models

import com.squareup.moshi.Json
import java.io.Serializable

/**
 * TheMovieDB standard recommended movies object.
 */
data class TMMoviesRecommended(

    val page: Int,
    val results: List<TMMovie>,

    @Json(name = "total_pages")
    val totalPages: Int,

    @Json(name = "total_results")
    val totalResults: Int,

    ) : Serializable
