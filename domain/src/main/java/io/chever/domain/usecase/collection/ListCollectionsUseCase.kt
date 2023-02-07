package io.chever.domain.usecase.collection

import io.chever.domain.model.collection.MediaItem
import io.chever.domain.model.common.ListCollectionParams
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import io.chever.domain.usecase.base.UseCaseParams
import io.chever.domain.usecase.movie.ListCollectionMoviesUseCase
import io.chever.domain.usecase.show.ListCollectionShowsUseCase
import javax.inject.Inject

class ListCollectionsUseCase @Inject constructor(
    private val listCollectionMoviesUseCase: ListCollectionMoviesUseCase,
    private val listCollectionShowsUseCase: ListCollectionShowsUseCase
) : UseCaseParams<ListCollectionParams, List<MediaItem>>() {

    override suspend fun run(
        params: ListCollectionParams
    ): AppResult<AppFailure, List<MediaItem>> {

        val listMediaItems = mutableListOf<MediaItem>()
        val errors = mutableListOf<AppFailure>()

        listCollectionMoviesUseCase(params) {
            when (it) {
                is AppResult.Success -> listMediaItems.addAll(it.value)
                is AppResult.Failure -> errors.add(it.value)
            }
        }

        listCollectionShowsUseCase(params) {
            when (it) {
                is AppResult.Success -> listMediaItems.addAll(it.value)
                is AppResult.Failure -> errors.add(it.value)
            }
        }

        return when {
            listMediaItems.isNotEmpty() -> AppResult.Success(listMediaItems.shuffled())
            else -> AppResult.Failure(ListCollectionError(errors))
        }
    }


    data class ListCollectionError(
        val issues: List<AppFailure>
    ) : AppFailure.FeatureFailure()
}