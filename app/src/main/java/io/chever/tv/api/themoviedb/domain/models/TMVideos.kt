package io.chever.tv.api.themoviedb.domain.models

import java.io.Serializable

/**
 * TheMovieDB standard videos results object.
 */
data class TMVideos(

    val id: Long,
    val results: List<TMVideoResult>,

    ) : Serializable
