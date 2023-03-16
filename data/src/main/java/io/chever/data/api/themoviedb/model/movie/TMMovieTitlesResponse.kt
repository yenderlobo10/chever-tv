package io.chever.data.api.themoviedb.model.movie

/**
 * TheMovieDB item alternative movie title result object.
 */
data class TMMovieTitlesResponse(

    val id: Long,
    val titles: List<TMMovieTitle>
)