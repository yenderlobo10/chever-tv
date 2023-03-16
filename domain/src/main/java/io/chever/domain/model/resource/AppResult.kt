package io.chever.domain.model.resource

/**
 * Represent the different types([L] or [R]) of a task result.
 *
 * @see Failure
 * @see Success
 */
sealed class AppResult<out L, out R> {

    /**
     * Indicates task has ended with errors.
     *
     * @param value Result<[L]> returned.
     */
    data class Failure<out L>(
        val value: L
    ) : AppResult<L, Nothing>()

    /**
     * Indicates task has ended successfully.
     *
     * @param value Result<[R]> returned.
     */
    data class Success<out R>(
        val value: R
    ) : AppResult<Nothing, R>()


    /**
     * Returns true if this is [Failure], otherwise false.
     */
    val isFailure get() = this is Failure<L>

    /**
     * Returns true if this is [Success], otherwise false.
     */
    val isSuccess get() = this is Success<R>

    /**
     * TODO: document
     */
    fun <T> mapSuccessTo(transform: (R) -> T) = when (this) {
        is Success -> Success(transform(this.value))
        is Failure -> Failure(this.value)
    }

    /**
     * TODO: document
     */
    fun <T> mapFailureTo(transform: (L) -> T) = when (this) {
        is Success -> Success(this.value)
        is Failure -> Failure(transform(this.value))
    }

    /**
     * Create a [AppResult.Failure] type.
     */
    fun <L> failure(value: L) = Failure(value)

    /**
     * Create a [AppResult.Success] type.
     */
    fun <R> success(value: R) = Success(value)
}