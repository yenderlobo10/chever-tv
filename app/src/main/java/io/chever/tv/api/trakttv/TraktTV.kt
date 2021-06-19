package io.chever.tv.api.trakttv

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import io.chever.tv.api.ApiClient
import io.chever.tv.api.trakttv.movies.TKTVMovies
import java.util.*

/**
 * Implement [ApiClient] to create a Trakt.tv API
 * end-points by services.
 */
@Suppress("MemberVisibilityCanBePrivate")
open class TraktTV : ApiClient() {

    /** API base url. */
    protected open val apiUrl = getAppProperty("traktTVApiUrl")

    /** API Key to client authentication. */
    protected val apiKey = getApiKey("traktTVApiKey")

    /** API value version identifier. */
    protected val apiVersion = getAppProperty("traktTVApiVersion")

    protected val defaultHeaders = mapOf(
        "trakt-api-version" to apiVersion,
        "trakt-api-key" to apiKey,
    )

    /** Custom [Moshi] with custom adapters. */
    protected val moshi: Moshi = with(moshi()) {

        add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
        build()
    }


    companion object {

        /**
         * Trakt.tv/movies API client end-points.
         * @see
         * <a href="https://trakt.docs.apiary.io/#reference/movies">API docs</a>
         */
        val movies = TKTVMovies().createApiService()
    }
}