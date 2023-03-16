package io.chever.data.api.trakttv.model.show

import com.squareup.moshi.Json

/**
 * Trakt.tv shows anticipated object response.
 */
data class TKShowsAnticipatedResponse(

    @Json(name = "list_count")
    val listCount: Int,

    val show: TKShowResponse,
)
