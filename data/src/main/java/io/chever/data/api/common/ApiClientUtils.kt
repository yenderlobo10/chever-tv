@file:Suppress("unused")

package io.chever.data.api.common

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.Date
import okhttp3.OkHttpClient
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Return an instance of [MoshiConverterFactory] for [moshi] instance.
 *
 * @param moshi Instance of [Moshi.Builder] to create factory,
 * if is not passed, return a factory create with [createMoshi] instance returned.
 */
fun createMoshiConverterFactoryOrDefault(
    moshi: Moshi? = null
): MoshiConverterFactory {
    return if (moshi !is Moshi)
        MoshiConverterFactory.create(createMoshi().build())
    else
        MoshiConverterFactory.create(moshi)
}

/**
 * Return an instance of [Moshi.Builder],
 * to create custom converter for JSON.
 */
fun createMoshi(): Moshi.Builder = Moshi.Builder().apply {

    add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
    addLast(KotlinJsonAdapterFactory())
}

/**
 * Return an instance of [OkHttpClient.Builder].
 * Eg. Use to extends functions like add headers to a request.
 */
fun createHttpClient() = OkHttpClient.Builder()