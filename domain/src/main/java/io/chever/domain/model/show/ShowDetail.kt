package io.chever.domain.model.show

import java.util.Date

data class ShowDetail(

    val id: Long,
    val name: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val genres: List<String>,
    val firstAirDate: Date?,
    val createdBy: List<String?>,
    val seasons: List<ShowSeason>,
    val episodeRunTimeAverage: Int,
    val lastEpisodeToAir: ShowEpisode?,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
    val lastAirDate: Date? = null,
    val homepage: String = "",
    val isAdult: Boolean = false,
    val originalLanguage: String = "",
    val originalName: String = "",
    val popularity: Float = 0f,
    val voteAverage: Float = 0f,
    val voteCount: Long = 0L,
    val status: String = "",
    val tagline: String = "",
    val inProduction: Boolean = false,
    val certification: String? = null
)
