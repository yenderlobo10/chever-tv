package io.chever.tv.common.extension

/**
 * Represent the different types of a task result.
 */
sealed class Result<out T> {

    // TODO: check this result
    // maybe receive data<T> param
    object Initial : Result<Nothing>()

    /**
     * Indicate task result is loading.
     */
    object Loading : Result<Nothing>()

    /**
     * Indicate task has finished successfully.
     *
     * @param data Result<[T]> returned by task.
     */
    data class Success<out T>(val data: T) : Result<T>()

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
    ) : Result<Nothing>()
}
