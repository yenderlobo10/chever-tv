package io.chever.data.api.themoviedb.model

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