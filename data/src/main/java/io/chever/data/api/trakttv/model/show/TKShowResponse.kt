package io.chever.data.api.trakttv.model.show

/**
 * Trakt.tv standard show object response.
 */
data class TKShowResponse(

    val title: String,
    val year: Int,
    val ids: TKShowIds
)