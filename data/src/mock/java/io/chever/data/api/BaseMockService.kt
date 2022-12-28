package io.chever.data.api

import android.content.Context
import androidx.annotation.RawRes
import com.squareup.moshi.Types
import io.chever.data.extension.MockUtil.parseRawJsonString
import io.chever.data.model.MockFailure
import io.chever.domain.model.result.AppFailure
import io.chever.domain.model.result.AppResult
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

open class BaseMockService(
    protected val context: Context
) {

    protected fun <T> readJsonToMockResponse(
        @RawRes rawJsonId: Int,
        errorMessage: String = "Mock failed",
        responseType: Type
    ): AppResult<AppFailure, T> = readJsonToMockResponse(
        rawJsonId = rawJsonId,
        errorMessage = errorMessage,
        responseTypes = arrayOf(responseType)
    )

    protected fun <T> readJsonToMockResponse(
        @RawRes rawJsonId: Int,
        errorMessage: String = "Mock failed",
        vararg responseTypes: Type
    ): AppResult<AppFailure, T> {

        val mockResponse = context.parseRawJsonString<T>(
            rawJsonId = rawJsonId,
            type = createParameterizedType(*responseTypes)
        )

        mockResponse?.let {
            return AppResult.Success(value = mockResponse)
        } ?: run {
            return AppResult.Failure(
                MockFailure(message = errorMessage)
            )
        }
    }

    private fun createParameterizedType(
        vararg types: Type
    ): ParameterizedType =
        if (types.size == 1) Types.newParameterizedType(
            types.first()
        )
        else Types.newParameterizedType(
            types.first(),
            *types.copyOfRange(1, types.size)
        )
}