package io.chever.apptv.model

import io.chever.domain.model.resource.AppFailure

sealed class ResourceState<out T> {

    object Empty : ResourceState<Nothing>()

    object Loading : ResourceState<Nothing>()

    data class Error(
        val message: String? = null,
        val issue: AppFailure? = null
    ) : ResourceState<Nothing>()

    data class Success<out T>(
        val data: T
    ) : ResourceState<T>()
}