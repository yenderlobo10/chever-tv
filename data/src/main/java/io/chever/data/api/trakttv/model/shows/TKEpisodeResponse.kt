package io.chever.data.api.trakttv.model.shows

/**
 * Trakt.tv standard episode object.
 */
data class TKEpisodeResponse(

    val season: Int,
    val number: Int,
    val title: String,
    val ids: TKEpisodeIds,
)