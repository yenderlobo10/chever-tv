package io.chever.tv.api.themoviedb.domain.models

import java.io.Serializable

/**
 * TheMovieDB standard movie credits object.
 */
data class TMMovieCredits(

    val id: Long,
    val cast: List<TMPersonCast>,

    /// Other properties not serialize:
    /// [crew]

) : Serializable
