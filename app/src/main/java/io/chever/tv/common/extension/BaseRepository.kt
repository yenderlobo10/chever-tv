package io.chever.tv.common.extension

import com.orhanobut.logger.Logger
import retrofit2.Response

abstract class BaseRepository {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {

        return try {

            val response = call()

            createResponse(response = response)

        } catch (ex: Exception) {

            createError(message = "", ex = ex)
        }
    }


    //#region Private methods

    private fun <T> createResponse(response: Response<T>): Result<T> {

        return if (response.isSuccessful) {

            Result.Success(response.body()!!)

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
    ): Result<T> {

        Logger.e(ex, "API Request Error ::\n$message \t $code")

        return Result.Error(
            message,
            exception = ex,
            code = code
        )
    }

    //#endregion
}