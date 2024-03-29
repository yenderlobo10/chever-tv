package io.chever.data.api.trakttv.model.show

/**
 * Trakt.tv shows trending object response.
 */
data class TKShowsTrendingResponse(

    val watchers: Int,
    val show: TKShowResponse,
)
