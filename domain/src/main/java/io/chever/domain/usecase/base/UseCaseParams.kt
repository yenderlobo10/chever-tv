package io.chever.domain.usecase.base

import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult

/**
 * Abstract class to represent a Use Case with params.
 *
 * ```
 * * Any Use Case that takes params in the application should
 *   implement this contract.
 */
abstract class UseCaseParams<in P, out T> where T : Any {

    suspend operator fun invoke(
        params: P,
        onResult: (AppResult<AppFailure, T>) -> Unit = {}
    ) = onResult(
        run(params)
    )

    abstract suspend fun run(
        params: P
    ): AppResult<AppFailure, T>
}