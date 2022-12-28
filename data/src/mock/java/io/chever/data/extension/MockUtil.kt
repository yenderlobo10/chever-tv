package io.chever.data.extension

import android.content.Context
import androidx.annotation.RawRes
import com.squareup.moshi.JsonDataException
import io.chever.data.api.common.MultipleDateJsonAdapter
import io.chever.data.api.common.createMoshi
import io.chever.shared.extension.readRawResourceLikeString
import java.lang.reflect.ParameterizedType
import java.util.Date

@Suppress("MemberVisibilityCanBePrivate")
object MockUtil {

    fun <T> Context.parseRawJsonString(
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
    fun <T> parseJsonString(
        json: String,
        type: ParameterizedType
    ): T? {

        return createMoshi()
            .add(Date::class.java, MultipleDateJsonAdapter().nullSafe())
            .build()
            .adapter<T>(type)?.fromJson(json)
    }

}