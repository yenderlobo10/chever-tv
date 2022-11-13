package io.chever.tv.api.opensubtitles.domain.models

import com.squareup.moshi.Json
import java.io.Serializable
import java.util.Date

data class OSDownloadResult(

    val link: String,

    @Json(name = "file_name")
    val fileName: String?,

    val requests: Int,
    val remaining: Int,

    @Json(name = "reset_time_utc")
    val resetTimeUtc: Date?

) : Serializable
