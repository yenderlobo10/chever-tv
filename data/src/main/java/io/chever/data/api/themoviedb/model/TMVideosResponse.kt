package io.chever.data.api.themoviedb.model

import io.chever.data.api.themoviedb.model.detail.TMVideo

/**
 * TheMovieDB standard videos results object.
 */
data class TMVideosResponse(

    val id: Long,
    val results: List<TMVideo>
)