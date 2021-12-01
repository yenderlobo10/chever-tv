package io.chever.tv.api.opensubtitles.apiservice

import io.chever.tv.api.opensubtitles.OpenSubtitles

class OpenSubsApiService : OpenSubtitles() {

    /**
     * Create [OpenSubtitles.api] client service.
     */
    fun createApiService(): OpenSubsService =
        createClient(baseUrl = apiUrl, moshi = moshi)
            .client(
                httpClient()
                    .addHeader(defaultHeaders)
                    .addLogger()
                    .build()
            )
            .build()
            .create(OpenSubsService::class.java)
}