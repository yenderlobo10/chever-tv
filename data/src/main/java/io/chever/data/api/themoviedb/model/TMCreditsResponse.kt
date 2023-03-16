package io.chever.data.api.themoviedb.model

import io.chever.data.api.themoviedb.model.detail.TMPersonCast

/**
 * TheMovieDB standard movie credits object.
 */
data class TMCreditsResponse(

    val id: Long,
    val cast: List<TMPersonCast>,

    /// Other properties not serialize:
    /// [crew]
)