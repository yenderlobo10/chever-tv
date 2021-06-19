package io.chever.tv.api.themoviedb.domain.models

/**
 * TheMovieDB standard movie credits object.
 */
data class TMMovieCredits(

    val id: Long,
    val cast: List<TMPeopleCast>,

    /// Other properties not serialize:
    /// [crew]
)
