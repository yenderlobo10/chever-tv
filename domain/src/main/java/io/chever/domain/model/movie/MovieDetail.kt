package io.chever.domain.model.movie

import java.util.Date

data class MovieDetail(

    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val genres: List<String>,
    val releaseAt: Date?,
    val runtime: Int,
    val homepage: String = "",
    val isAdult: Boolean = false,
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val popularity: Float = 0f,
    val voteAverage: Float = 0f,
    val voteCount: Long = 0L,
    val budget: Double = 0.0,
    val revenue: Long = 0L,
    val status: String = "",
    val tagline: String = "",
    val hasVideo: Boolean = false,
    val certification: String? = null,
    val imdbId: String? = null
)
