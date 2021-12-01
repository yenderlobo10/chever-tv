package io.chever.tv.common.extension

import retrofit2.Response
import timber.log.Timber

abstract class AppBaseRepository {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): AppResult<T> {

        return try {

            val response = call()

            createResponse(response = response)

        } catch (ex: Exception) {

            createError(message = "", ex = ex)
        }
    }


    //#region Private methods

    private fun <T> createResponse(response: Response<T>): AppResult<T> {

        return if (response.isSuccessful) {

            AppResult.Success(response.body()!!)

        } else {

            createError(
                message = response.message(),
                code = response.code()
            )
        }
    }

    private fun <T> createError(
        message: String,
        code: Int? = 0,
        ex: Exception? = null,
    ): AppResult<T> {

        Timber.e(ex, "API Request Error ::\n$message \t $code")

        return AppResult.Error(
            message,
            exception = ex,
            code = code
        )
    }

    //#endregion
}