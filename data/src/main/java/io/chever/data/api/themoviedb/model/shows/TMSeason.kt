package io.chever.data.api.themoviedb.model.shows

import com.squareup.moshi.Json
import java.util.Date

data class TMSeason(

    @Json(name = "air_date")
    val airDate: Date?,

    @Json(name = "episode_count")
    val episodeCount: Int = 0,

    val id: Long,
    val name: String = "",
    val overview: String?,

    @Json(name = "poster_path")
    val posterPath: String?,

    @Json(name = "season_number")
    val seasonNumber: Int = 0
)
