@file:Suppress("unused")

package io.chever.data.api.common

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


/**
 * Enabled logger interceptor to request/response on api client service.
 *
 * @param isDebug Control register interceptor build variants.
 * @param level Optional - Any value in [HttpLoggingInterceptor.Level].
 */
fun OkHttpClient.Builder.addLogger(
    isDebug: Boolean = false,
    level: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BASIC
): OkHttpClient.Builder {

    if (isDebug)
        this.addInterceptor(HttpLoggingInterceptor().apply {
            this.level = level
        })

    return this
}

/**
 * Add an header property to a request on api client service.
 *
 * @param name KeyName of header.
 * @param value Value of header.
 */
fun OkHttpClient.Builder.addHeader(
    name: String,
    value: String
): OkHttpClient.Builder {

    this.addInterceptor(Interceptor { chain ->

        val originRequest = chain.request()

        // Validate exist header
        val hasHeader = originRequest.headers.names().contains(name)

        if (hasHeader) return@Interceptor chain.proceed(originRequest)

        // Add new header
        val newRequest = originRequest.newBuilder()
            .addHeader(name, value)
            .build()

        return@Interceptor chain.proceed(newRequest)
    })

    return this
}

/**
 * Add any header properties to a request on api client service.
 *
 * @param headers Map<Key, Value> of headers.
 */
fun OkHttpClient.Builder.addHeaders(
    headers: Map<String, String>
): OkHttpClient.Builder {

    headers.forEach { item ->

        addHeader(item.key, item.value)
    }

    return this
}

/**
 * Add an query parameter to a request on api client service.
 *
 * @param name Name of query param.
 * @param value Value of query param.
 */
fun OkHttpClient.Builder.addQueryParameter(
    name: String,
    value: String
): OkHttpClient.Builder {

    this.addInterceptor(Interceptor { chain ->

        val originRequest = chain.request()

        // Validate exist param
        val hasParameter = originRequest.url.queryParameterNames.contains(name)

        if (hasParameter) return@Interceptor chain.proceed(originRequest)

        // Add new param
        val newUrl = originRequest.url.newBuilder()
        newUrl.addQueryParameter(name, value)

        val newRequest = originRequest.newBuilder()
            .url(newUrl.build())
            .build()

        return@Interceptor chain.proceed(newRequest)
    })

    return this
}

/**
 * Add any query parameters to a request on api client service.
 *
 * @param queries Map<Key, Value> of query params.
 */
fun OkHttpClient.Builder.addQueryParameters(
    queries: Map<String, String>
): OkHttpClient.Builder {

    queries.forEach { item ->

        addQueryParameter(item.key, item.value)
    }

    return this
}