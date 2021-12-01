package io.chever.tv.api.opensubtitles.apiservice

import retrofit2.http.GET

interface OpenSubsService {

    @GET("subtitles")
    suspend fun subtitles()
}