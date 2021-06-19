package io.chever.tv.api.themoviedb.movies

import io.chever.tv.api.themoviedb.TheMovieDB

class TMDBMovies : TheMovieDB() {

    override val apiUrl: String = super.apiUrl.plus("movie/")

    /**
     * Create [TheMovieDB.movies] api client service.
     */
    fun createApiService(): TMDBMoviesService =
        createClient(baseUrl = apiUrl, moshi = moshi)
            .client(
                httpClient()
                    .addQueryParameter(defaultQueryParameters)
                    .addLogger()
                    .build()
            )
            .build()
            .create(TMDBMoviesService::class.java)
}