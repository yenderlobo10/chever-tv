package io.chever.data.api.themoviedb.model

import java.io.Serializable

/**
 * TheMovieDB standard genre object.
 */
data class TMGenre(

    val id: Int,
    val name: String

) : Serializable