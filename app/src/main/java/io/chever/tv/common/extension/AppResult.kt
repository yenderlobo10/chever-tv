package io.chever.tv.common.extension

/**
 * Represent the different types of a task result.
 */
sealed class AppResult<out T> {

    /**
     * Indicate task result not has data.
     */
    object Empty : AppResult<Nothing>()

    /**
     * Indicate task result is loading.
     */
    object Loading : AppResult<Nothing>()

    /**
     * Indicate task has finished successfully.
     *
     * @param data Result<[T]> returned by task.
     */
    data class Success<out T>(val data: T) : AppResult<T>()

    /**
     * Indicate task has produced exceptions.
     *
     * @param message Optional - Error short description.
     * @param code Optional - Ref. code of error (ej. HTTP Status code).
     * @param exception Optional - exception object.
     */
    data class Error(
        val message: String?,
        val code: Int? = null,
        val exception: Exception? = null
    ) : AppResult<Nothing>()
}
