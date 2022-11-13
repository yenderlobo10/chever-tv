package io.chever.tv.api.opensubtitles.apiservice

import io.chever.tv.api.opensubtitles.domain.models.OSDownloadResult
import io.chever.tv.api.opensubtitles.domain.models.OSSubtitleFile
import io.chever.tv.api.opensubtitles.domain.models.OSSubtitlesResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface OpenSubsService {

    @GET("subtitles")
    suspend fun subtitles(
        @Query("year") year: Int,
        @Query("languages") languages: String,
        @Query("query", encoded = true) query: String?,
        @Query("imdb_id") imdbId: String? = null,
        @Query("tmdb_id") tmdbId: Long? = null,
    ): Response<OSSubtitlesResult>

    @Headers("Accept: */*")
    @POST("download")
    suspend fun download(
        @Body file: OSSubtitleFile
    ): Response<OSDownloadResult>
}