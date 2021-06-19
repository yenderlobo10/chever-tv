package io.chever.tv.api.trakttv.domain.models

/**
 * Trakt.tv standard show object.
 */
data class TKShow(

    val title: String,
    val year: Int,
    val ids: TKShowIds,
)
