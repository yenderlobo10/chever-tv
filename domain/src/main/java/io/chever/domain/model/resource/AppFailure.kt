package io.chever.domain.model.resource

/**
 * List generic failure types when executing a task.
 *
 * @see AppResult.Failure
 */
@Suppress("unused")
sealed class AppFailure {

    /**
     * Indicate device is not internet connection.
     * TODO: implement Retrofit handler
     */
    object NonInternetConnection : AppFailure()

    /**
     * Indicate server response error.
     */
    object ServerError : AppFailure()

    /**
     * Indicate an unknown error.
     */
    object GenericError : AppFailure()

    /**
     * Extend to implement feature specific failures.
     */
    abstract class FeatureFailure : AppFailure()
}