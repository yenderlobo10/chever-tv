package io.chever.data.api.themoviedb.model

import java.io.Serializable

/**
 * TheMovieDB standard videos results object.
 */
data class TMVideosResponse(

    val id: Long,
    val results: List<TMVideo>

) : Serializable