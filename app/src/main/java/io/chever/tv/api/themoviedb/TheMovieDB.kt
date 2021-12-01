package io.chever.tv.api.themoviedb

import com.squareup.moshi.Moshi
import io.chever.tv.api.ApiClient
import io.chever.tv.api.common.MultipleDateJsonAdapter
import io.chever.tv.api.themoviedb.movies.TMDBMovies
import java.util.*

@Suppress("MemberVisibilityCanBePrivate")
open class TheMovieDB : ApiClient() {

    /** API base url. */
    protected open val apiUrl = getAppProperty("theMovieDBApiUrl")

    /** API Key to client authentication. */
    protected val apiKey = getApiKey("theMovieDBApiKey")

    /** API default language value. */
    protected val apiDefaultLang = getAppProperty("theMovieDBDefaultLanguage")

    /** Default api query params. */
    protected val defaultQueryParameters = mapOf(
        "api_key" to apiKey,
        "language" to apiDefaultLang,
    )

    /** Custom [Moshi] with custom adapters. */
    protected val moshi: Moshi = with(moshi()) {

        add(Date::class.java, MultipleDateJsonAdapter().nullSafe())
        build()
    }


    companion object {

        /**
         * TheMovieDB movies API end-points.
         * @see
         * <a href="https://developers.themoviedb.org/3/movies">API docs</a>
         */
        val movies = TMDBMovies().createApiService()
    }
}