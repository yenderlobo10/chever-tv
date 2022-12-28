package io.chever.data.api

import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

/**
 * Base to create api client services by [Retrofit].
 */
abstract class ApiClient {

    abstract val baseUrl: String

    abstract val converterFactory: Converter.Factory

    abstract val httpClient: OkHttpClient?


    /**
     * Create an instance of [Retrofit].
     */
    open fun retrofit(): Retrofit = with(Retrofit.Builder().apply {

        baseUrl(baseUrl)
        addConverterFactory(converterFactory)
        httpClient?.let { client(it) }

    }) {
        build()
    }
}