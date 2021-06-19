package io.chever.tv.api.trakttv.domain.models

/**
 * Trakt.tv standard episode object.
 */
data class TKEpisode(

    val season: Int,
    val number: Int,
    val title: String,
    val ids: TKEpisodeIds,
)
