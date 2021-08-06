package io.chever.tv.api.themoviedb.domain.models

import com.squareup.moshi.Json
import java.io.Serializable

/**
 * TheMovieDB standard similar movies object.
 */
data class TMMoviesSimilar(

    val page: Int,
    val results: List<TMMovie>,

    @Json(name = "total_pages")
    val totalPages: Int,

    @Json(name = "total_results")
    val totalResults: Int,

    ) : Serializable