package io.chever.data.api.trakttv.model.show

import com.squareup.moshi.Json

/**
 * Trakt.tv shows recommended object response.
 */
data class TKShowsRecommendedResponse(

    @Json(name = "user_count")
    val userCount: Int,

    val show: TKShowResponse,
)
