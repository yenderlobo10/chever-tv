package io.chever.data.api.trakttv

import io.chever.data.api.ApiClient
import io.chever.data.api.common.addHeaders
import io.chever.data.api.common.addLogger
import io.chever.data.api.common.createHttpClient
import io.chever.data.api.common.createMoshi
import io.chever.data.api.common.createMoshiConverterFactoryOrDefault
import javax.inject.Inject
import okhttp3.OkHttpClient
import retrofit2.Converter

class TraktTVApiClient @Inject constructor(
    config: TraktTVApiConfig
) : ApiClient() {

    override val baseUrl = config.baseUrl

    override val converterFactory: Converter.Factory =
        createMoshiConverterFactoryOrDefault(
            moshi = createMoshi().build()
        )

    override val httpClient: OkHttpClient = createHttpClient()
        .addHeaders(config.defaultHeaders)
        .addLogger()
        .build()
}