package io.chever.data.api.trakttv.model

/**
 * Trakt.tv standard show object.
 */
data class TKShow(

    val title: String,
    val year: Int,
    val ids: TKShowIds,
)