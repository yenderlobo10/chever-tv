package io.chever.tv.api.opensubtitles

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import io.chever.tv.api.ApiClient
import io.chever.tv.api.opensubtitles.apiservice.OpenSubsApiService
import java.util.*

@Suppress("MemberVisibilityCanBePrivate")
open class OpenSubtitles : ApiClient() {

    /** API base url. */
    protected open val apiUrl = getAppProperty("openSubtitlesApiUrl")

    /** API Key to client authentication. */
    protected val apiKey = getApiKey("openSubtitlesApiKey")

    /** Default API headers to all end-points */
    protected val defaultHeaders = mapOf(
        "Api-Key" to apiKey
    )

    /** Custom [Moshi] with custom adapters. */
    protected val moshi: Moshi = with(moshi()) {

        add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
        build()
    }

    companion object {

        /**
         * Open Subtitles API client end-points.
         * @see
         * <a href="https://opensubtitles.stoplight.io/docs/opensubtitles-api/open_api.json">API docs</a>
         */
        val api = OpenSubsApiService().createApiService()
    }
}