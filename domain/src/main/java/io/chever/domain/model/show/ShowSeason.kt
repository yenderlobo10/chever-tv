package io.chever.domain.model.show

import java.util.Date

data class ShowSeason(

    val id: Long,
    val name: String,
    val overview: String,
    val posterPath: String,
    val airDate: Date?,
    val episodeCount: Int,
    val number: Int
)
