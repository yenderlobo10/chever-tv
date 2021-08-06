package io.chever.tv.ui.common.models

import io.chever.tv.api.themoviedb.domain.models.TMMovieDetail
import io.chever.tv.ui.movies.common.enums.MovieCollection
import java.io.Serializable

/**
 * TODO: document class
 */
data class MovieCardItem(

    val title: String,
    val year: Int,
    val collection: MovieCollection,

    var idTMDB: Long? = null,
    var idTKTV: Long? = null,
    var detail: TMMovieDetail? = null,
    var chips: MutableList<String> = mutableListOf(),

    ): Serializable