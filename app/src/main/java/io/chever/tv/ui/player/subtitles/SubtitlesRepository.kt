package io.chever.tv.ui.player.subtitles

import io.chever.tv.api.opensubtitles.OpenSubtitles
import io.chever.tv.api.opensubtitles.domain.models.OSSubtitleFile
import io.chever.tv.common.extension.AppBaseRepository

class SubtitlesRepository : AppBaseRepository() {

    suspend fun subtitles(
        year: Int,
        languages: List<String>,
        query: String?,
        imdbId: String? = null,
        tmdbId: Long? = null
    ) = getResult {

        OpenSubtitles.api.subtitles(
            year = year,
            languages = languages.joinToString(","),
            query = query,
            imdbId = imdbId,
            tmdbId = tmdbId
        )
    }

    suspend fun download(
        file: OSSubtitleFile
    ) = getResult {

        OpenSubtitles.api.download(file)
    }
}