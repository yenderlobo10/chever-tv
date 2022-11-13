package io.chever.tv.api.opensubtitles.domain.models

import com.squareup.moshi.Json

data class OSSubtitleFile(

    @Json(name = "file_id")
    val id: Long,
    @Json(name = "file_name")
    val name: String?
)
