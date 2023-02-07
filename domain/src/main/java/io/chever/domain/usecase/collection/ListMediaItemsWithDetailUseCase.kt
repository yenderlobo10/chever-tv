package io.chever.domain.usecase.collection

import io.chever.domain.model.collection.MediaItem
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import javax.inject.Inject

class ListMediaItemsWithDetailUseCase @Inject constructor(
    private val getMediaItemDetailUseCase: GetMediaItemDetailUseCase
) {

    suspend operator fun invoke(
        request: suspend () -> AppResult<AppFailure, List<MediaItem>>
    ): AppResult<AppFailure, List<MediaItem>> = when (
        val response = request()
    ) {
        is AppResult.Success -> {
            val newListItems = mutableListOf<MediaItem>()

            response.value.forEach { item ->
                getMediaItemDetailUseCase(
                    params = GetMediaItemDetailUseCase.Params(
                        id = item.id,
                        mediaType = item.type
                    )
                ) { result ->
                    newListItems.add(
                        when (result) {
                            is AppResult.Success -> item.copy(
                                detail = result.value
                            )
                            is AppResult.Failure -> item
                        }
                    )
                }
            }

            AppResult.Success(newListItems)
        }

        is AppResult.Failure -> AppResult.Failure(response.value)
    }
}