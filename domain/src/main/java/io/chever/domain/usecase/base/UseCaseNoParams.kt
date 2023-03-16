package io.chever.domain.usecase.base

import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult

/**
 * Abstract class to represent a Use Case that takes no params.
 *
 * ```
 * * Any Use Case that takes no params in the application should
 *   implement this contract.
 */
abstract class UseCaseNoParams<out T> where T : Any {

    suspend operator fun invoke(
        onResult: (AppResult<AppFailure, T>) -> Unit = {}
    ) = onResult(
        run()
    )

    abstract suspend fun run(): AppResult<AppFailure, T>
}