package io.chever.domain.usecase.movie

import io.chever.domain.enums.ListCollection
import io.chever.domain.model.collection.MediaItem
import io.chever.domain.model.common.ListCollectionParams
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import io.chever.domain.repository.MovieRepository
import io.chever.domain.usecase.base.UseCaseParams
import io.chever.domain.usecase.collection.ListMediaItemsWithDetailUseCase
import io.chever.shared.observability.AppLogger
import javax.inject.Inject

class ListCollectionMoviesUseCase @Inject constructor(
    private val moviesRepository: MovieRepository,
    private val listMediaItemsWithDetailUseCase: ListMediaItemsWithDetailUseCase
) : UseCaseParams<ListCollectionParams, List<MediaItem>>() {

    override suspend fun run(
        params: ListCollectionParams
    ): AppResult<AppFailure, List<MediaItem>> = try {

        listMediaItemsWithDetailUseCase {

            when (params.collection) {

                ListCollection.Watched -> moviesRepository.watched(
                    period = params.period,
                    limit = params.limit
                )

                ListCollection.Recommended -> moviesRepository.recommended(
                    period = params.period,
                    limit = params.limit
                )

                ListCollection.Collected -> moviesRepository.collected(
                    period = params.period,
                    limit = params.limit
                )

                ListCollection.Popular -> moviesRepository.popular(
                    period = params.period,
                    limit = params.limit
                )

                ListCollection.Anticipated -> moviesRepository.anticipated(
                    period = params.period,
                    limit = params.limit
                )
            }
        }

    } catch (ex: Exception) {

        AppLogger.error(ex)
        AppResult.Failure(ListCollectionMoviesError(ex))
    }

    data class ListCollectionMoviesError(
        val error: Exception
    ) : AppFailure.FeatureFailure()
}