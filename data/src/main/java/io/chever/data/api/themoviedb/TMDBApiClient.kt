package io.chever.data.api.themoviedb

import io.chever.data.api.ApiClient
import io.chever.data.api.common.MultipleDateJsonAdapter
import io.chever.data.api.common.addLogger
import io.chever.data.api.common.addQueryParameters
import io.chever.data.api.common.createHttpClient
import io.chever.data.api.common.createMoshi
import io.chever.data.api.common.createMoshiConverterFactoryOrDefault
import java.util.Date
import javax.inject.Inject
import okhttp3.OkHttpClient
import retrofit2.Converter

open class TMDBApiClient @Inject constructor(
    config: TMDBApiConfig
) : ApiClient() {

    override val baseUrl: String = config.baseUrl

    override val converterFactory: Converter.Factory =
        createMoshiConverterFactoryOrDefault(
            moshi = with(createMoshi()) {
                add(Date::class.java, MultipleDateJsonAdapter().nullSafe())
                build()
            }
        )

    override val httpClient: OkHttpClient = createHttpClient()
        .addQueryParameters(config.defaultQueryParameters)
        .addLogger()
        .build()
}