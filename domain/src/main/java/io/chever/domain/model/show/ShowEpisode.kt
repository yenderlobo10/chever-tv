package io.chever.domain.model.show

import java.util.Date

data class ShowEpisode(

    val id: Long,
    val name: String,
    val overview: String,
    val airDate: Date?,
    val stillPath: String?,
    val showId: Long?,
    val seasonNumber: Int,
    val number: Int,
    val runtime: Int = 0,
    val voteAverage: Float = 0f,
    val voteCount: Int = 0
)
