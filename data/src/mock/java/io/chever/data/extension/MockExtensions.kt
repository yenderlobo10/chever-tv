package io.chever.data.extension

import android.content.Context
import androidx.annotation.RawRes
import com.squareup.moshi.JsonDataException
import io.chever.data.api.common.MultipleDateJsonAdapter
import io.chever.data.api.common.createMoshi
import io.chever.shared.extension.readRawResourceLikeString
import java.lang.reflect.ParameterizedType
import java.util.Date


internal fun <T> Context.parseRawJsonString(
    @RawRes rawJsonId: Int,
    type: Class<T>
): T? {

    val jsonMockResponse = this.resources.readRawResourceLikeString(rawJsonId)

    return parseJsonString(
        json = jsonMockResponse,
        type = type
    )
}

internal fun <T> Context.parseRawJsonString(
    @RawRes rawJsonId: Int,
    type: ParameterizedType
): T? {

    val jsonMockResponse = this.resources.readRawResourceLikeString(rawJsonId)

    return parseJsonString(
        json = jsonMockResponse,
        type = type
    )
}

@Throws(JsonDataException::class)
internal fun <T> parseJsonString(
    json: String,
    type: ParameterizedType
): T? {

    return createMoshi()
        .add(Date::class.java, MultipleDateJsonAdapter().nullSafe())
        .build()
        .adapter<T>(type)?.fromJson(json)
}

@Throws(JsonDataException::class)
internal fun <T> parseJsonString(
    json: String,
    type: Class<T>
): T? {

    return createMoshi()
        .add(Date::class.java, MultipleDateJsonAdapter().nullSafe())
        .build()
        .adapter(type)?.fromJson(json)
}