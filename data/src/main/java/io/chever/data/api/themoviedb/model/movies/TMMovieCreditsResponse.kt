package io.chever.data.api.themoviedb.model.movies

import io.chever.data.api.themoviedb.model.TMPersonCast
import java.io.Serializable

/**
 * TheMovieDB standard movie credits object.
 */
data class TMMovieCreditsResponse(

    val id: Long,
    val cast: List<TMPersonCast>,

    /// Other properties not serialize:
    /// [crew]

) : Serializable