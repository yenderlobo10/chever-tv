package io.chever.tv.api.opensubtitles.domain.models

import com.squareup.moshi.Json
import java.io.Serializable

data class OSSubtitlesResult(

    @Json(name = "total_pages")
    val totalPages: Int,

    @Json(name = "total_count")
    val totalCount: Int,

    @Json(name = "per_page")
    val perPage: Int,

    val page: Int,
    val data: List<OSSubtitle>

) : Serializable
