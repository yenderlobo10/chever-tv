package io.chever.tv.api.opensubtitles.domain.models

import com.squareup.moshi.Json
import java.util.Date

data class OSSubtitleAttributes(

    @Json(name = "subtitle_id")
    val id: String?,

    val language: String,

    @Json(name = "download_count")
    val downloadCount: Long,

    @Json(name = "new_download_count")
    val newDownloadCount: Long,

    @Json(name = "hd")
    val isHD: Boolean,

    val votes: Long,
    val ratings: Float,

    @Json(name = "from_trusted")
    val isFromTrusted: Boolean,

    @Json(name = "upload_date")
    val uploadDate: Date?,

    @Json(name = "ai_translated")
    val isAITranslated: Boolean,

    @Json(name = "machine_translated")
    val isMachineTranslated: Boolean,

    val release: String,
    val comments: String?,
    val files: List<OSSubtitleFile>
)
