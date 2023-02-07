package io.chever.data.api

import android.content.Context
import androidx.annotation.RawRes
import com.squareup.moshi.Types
import io.chever.data.extension.parseRawJsonString
import io.chever.data.model.MockFailure
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import java.lang.reflect.Type

open class BaseMockService(
    protected val context: Context
) {

    protected fun <T> readJsonToMockResponse(
        @RawRes rawJsonId: Int,
        errorMessage: String = "Mock failed",
        responseType: Class<T>
    ): AppResult<AppFailure, T> {

        val mockResponse = context.parseRawJsonString(
            rawJsonId = rawJsonId,
            type = responseType
        )

        mockResponse?.let {
            return AppResult.Success(value = mockResponse)
        } ?: run {
            return AppResult.Failure(
                MockFailure(message = errorMessage)
            )
        }
    }

    protected fun <T> readJsonToMockResponse(
        @RawRes rawJsonId: Int,
        errorMessage: String = "Mock failed",
        vararg responseTypes: Type
    ): AppResult<AppFailure, T> {

        val mockResponse = context.parseRawJsonString<T>(
            rawJsonId = rawJsonId,
            type = Types.newParameterizedType(
                responseTypes.first(),
                *responseTypes.copyOfRange(1, responseTypes.size)
            )
        )

        mockResponse?.let {
            return AppResult.Success(value = mockResponse)
        } ?: run {
            return AppResult.Failure(
                MockFailure(message = errorMessage)
            )
        }
    }
}