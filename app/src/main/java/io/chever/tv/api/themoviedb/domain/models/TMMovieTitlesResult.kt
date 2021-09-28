package io.chever.tv.api.themoviedb.domain.models

import java.io.Serializable

/**
 * TheMovieDB item alternative movie title result object.
 */
data class TMMovieTitlesResult(

    val id: Long,
    val titles: List<TMMovieTitle>,

    ) : Serializable
