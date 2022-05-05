package io.chever.tv.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.chever.tv.BuildConfig
import io.chever.tv.CheverApp
import io.chever.tv.common.extension.AppConstants
import io.chever.tv.common.extension.Extensions.loadProperties
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * TODO: document class
 */
@Suppress(
    "unused",
    "MemberVisibilityCanBePrivate",
    "SameParameterValue",
)
open class ApiClient {

    /**
     * Create an instance of [Retrofit.Builder].
     * @param baseUrl Api client base url.
     */
    fun createClient(
        baseUrl: String,
        moshi: Moshi? = null,
    ) =
        Retrofit.Builder().apply {

            val converter =
                if (moshi !is Moshi)
                    MoshiConverterFactory.create(moshi().build())
                else
                    MoshiConverterFactory.create(moshi)

            baseUrl(baseUrl)
            addConverterFactory(converter)
        }


    /**
     * Return an instance of [Moshi.Builder],
     * to create custom converter.
     */
    fun moshi(): Moshi.Builder = Moshi.Builder().apply {
        addLast(KotlinJsonAdapterFactory())
    }


    /**
     * Return an instance of [OkHttpClient.Builder],
     * to use with extends methods like [addHeader].
     */
    fun httpClient() = OkHttpClient.Builder()


    /**
     * Get value of variable from default file
     * of app properties.
     */
    protected fun getAppProperty(name: String) =
        CheverApp.context
            .loadProperties(AppConstants.appPropertiesFileName)

            .getProperty(name, "")!!

    /**
     * Get key value from default file of app
     * keys properties.
     */
    protected fun getApiKey(keyName: String) =
        CheverApp.context
            .loadProperties(AppConstants.appKeysPropertiesFileName)
            .getProperty(keyName, "")!!


    //#region Extensions methods

    /**
     * Enabled logger interceptor to request/response
     * on [Retrofit.Builder] api client service.
     */
    fun OkHttpClient.Builder.addLogger(): OkHttpClient.Builder {

        if (BuildConfig.DEBUG) {

            this.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }

        return this
    }

    /**
     * Add an header property to a request
     * on api client service.
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
     * Add any header properties to a request
     * on api client service.
     */
    fun OkHttpClient.Builder.addHeader(
        headers: Map<String, String>
    ): OkHttpClient.Builder {

        headers.forEach { item ->

            addHeader(item.key, item.value)
        }

        return this
    }

    /**
     * Add an query parameter to a request
     * on api client service.
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
     * Add any query parameters to a request
     * on api client service.
     */
    fun OkHttpClient.Builder.addQueryParameter(
        queries: Map<String, String>
    ): OkHttpClient.Builder {

        queries.forEach { item ->

            addQueryParameter(item.key, item.value)
        }

        return this
    }

    //#endregion

}