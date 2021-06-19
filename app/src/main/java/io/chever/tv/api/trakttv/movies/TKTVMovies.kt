package io.chever.tv.api.trakttv.movies

import io.chever.tv.api.trakttv.TraktTV

class TKTVMovies : TraktTV() {

    override val apiUrl = super.apiUrl.plus("movies/")


    /**
     * Create [TraktTV.movies] api client service.
     */
    fun createApiService(): TKTVMoviesService =
        createClient(baseUrl = apiUrl, moshi = moshi)
            .client(
                httpClient()
                    .addHeader(defaultHeaders)
                    .addLogger()
                    .build()
            )
            .build()
            .create(TKTVMoviesService::class.java)
}