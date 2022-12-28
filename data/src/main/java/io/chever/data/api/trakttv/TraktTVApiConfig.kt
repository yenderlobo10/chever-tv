package io.chever.data.api.trakttv

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.chever.data.api.common.ApiConfig
import javax.inject.Inject

@Suppress("MemberVisibilityCanBePrivate", "unused")
class TraktTVApiConfig @Inject constructor(
    @ApplicationContext context: Context
) : ApiConfig(context) {

    override val baseUrl = "https://api.trakt.tv/"
    val apiVersion = "2"
    override val apiKey: String = secrets.getProperty(secretKey)

    /** Default api query params. */
    val defaultHeaders = mapOf(
        "trakt-api-version" to apiVersion,
        "trakt-api-key" to apiKey
    )

    companion object {

        private const val secretKey = "traktTVApiKey"
    }
}