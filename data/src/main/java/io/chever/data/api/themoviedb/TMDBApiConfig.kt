package io.chever.data.api.themoviedb

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.chever.data.api.common.ApiConfig
import javax.inject.Inject

@Suppress("MemberVisibilityCanBePrivate", "unused")
class TMDBApiConfig @Inject constructor(
    @ApplicationContext context: Context
) : ApiConfig(context) {

    override val baseUrl = "https://api.themoviedb.org/3/"
    override val apiKey: String = secrets.getProperty(secretKey, "")

    /** Default api query params. */
    val defaultQueryParameters = mapOf(
        "api_key" to apiKey,
        "language" to defaultLanguage
    )

    companion object {

        private const val secretKey = "theMovieDBApiKey"

        const val imagesUrl = "https://image.tmdb.org/t/p/"
        const val defaultLanguage = "es-MX"
    }
}