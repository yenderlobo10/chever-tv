package io.chever.tv.api.themoviedb.domain.models

import java.io.Serializable


/**
 * TheMovieDB standard genre object.
 */
data class TMGenre(

    val id: Int,
    val name: String,

    ) : Serializable