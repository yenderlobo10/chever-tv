package io.chever.data.api.themoviedb.model.show

import com.squareup.moshi.Json
import java.util.Date

data class TMEpisode(

    @Json(name = "air_date")
    val airDate: Date?,

    @Json(name = "episode_number")
    val episodeNumber: Int?,

    val id: Long,
    val name: String?,
    val overview: String?,

    @Json(name = "production_code")
    val productionCode: String?,

    val runtime: Int = 0,

    @Json(name = "season_number")
    val seasonNumber: Int = 1,

    @Json(name = "show_id")
    val showId: Long?,

    @Json(name = "still_path")
    val stillPath: String?,

    @Json(name = "vote_average")
    val voteAverage: Float = 0f,

    @Json(name = "vote_count")
    val voteCount: Int = 0
)
