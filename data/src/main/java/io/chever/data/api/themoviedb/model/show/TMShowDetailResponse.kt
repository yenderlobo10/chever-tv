package io.chever.data.api.themoviedb.model.show

import com.squareup.moshi.Json
import io.chever.data.api.themoviedb.model.detail.TMCompany
import io.chever.data.api.themoviedb.model.detail.TMCountry
import io.chever.data.api.themoviedb.model.detail.TMGenre
import io.chever.data.api.themoviedb.model.TMResultsResponse
import io.chever.data.api.themoviedb.model.detail.TMSpokenLanguage
import java.util.Date

/**
 * TheMovieDB standard show details object.
 */
data class TMShowDetailResponse(

    val adult: Boolean,

    @Json(name = "backdrop_path")
    val backdropPath: String?,

    @Json(name = "created_by")
    val createdBy: List<TMShowCreator>,

    @Json(name = "episode_run_time")
    val episodeRunTime: List<Int>,

    @Json(name = "first_air_date")
    val firstAirDate: Date?,

    val genres: List<TMGenre>,
    val homepage: String?,
    val id: Long,

    @Json(name = "in_production")
    val inProduction: Boolean,

    @Json(name = "last_air_date")
    val lastAirDate: Date?,

    @Json(name = "last_episode_to_air")
    val lastEpisodeToAir: TMEpisode?,

    val name: String = "",

    val networks: List<TMCompany>,

    @Json(name = "number_of_episodes")
    val numberOfEpisodes: Int = 0,

    @Json(name = "number_of_seasons")
    val numberOfSeasons: Int = 0,

    @Json(name = "original_language")
    val originalLanguage: String?,

    @Json(name = "original_name")
    val originalName: String = "",

    val overview: String?,
    val popularity: Float = 0f,

    @Json(name = "poster_path")
    val posterPath: String?,

    @Json(name = "production_companies")
    val productionCompanies: List<TMCompany>,

    @Json(name = "production_countries")
    val productionCountries: List<TMCountry>,

    val seasons: List<TMSeason>,

    @Json(name = "spoken_languages")
    val spokenLanguages: List<TMSpokenLanguage>,

    val status: String?,
    val tagline: String?,

    @Json(name = "vote_average")
    val voteAverage: Float = 0f,

    @Json(name = "vote_count")
    val voteCount: Long = 0L,

    @Json(name = "content_ratings")
    val certifications: TMResultsResponse<TMShowCertification>?
)
