package io.chever.tv.common.models

import io.chever.tv.ui.movies.common.enums.MovieCollection

/**
 * TODO: document class
 */
data class MovieCardItem(

    val title: String,
    val year: Int,
    val collection: MovieCollection,

    var idTMDB: Long? = null,
    var idTKTV: Long? = null,
    var transTitle: String = "",
    var backdropUrl: String? = "",
    var releaseDate: String? = "",
    var chips: MutableList<String> = mutableListOf(),
)
