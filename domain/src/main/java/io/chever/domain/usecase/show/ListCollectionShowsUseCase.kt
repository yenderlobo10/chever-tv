package io.chever.domain.usecase.show

import io.chever.domain.enums.ListCollection
import io.chever.domain.model.media.MediaItem
import io.chever.domain.model.resource.ListCollectionParams
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import io.chever.domain.repository.ShowRepository
import io.chever.domain.usecase.base.UseCaseParams
import io.chever.domain.usecase.media.ListMediaItemsWithDetailUseCase
import io.chever.shared.observability.AppLogger
import javax.inject.Inject

class ListCollectionShowsUseCase @Inject constructor(
    private val showsRepository: ShowRepository,
    private val listMediaItemsWithDetailUseCase: ListMediaItemsWithDetailUseCase
) : UseCaseParams<ListCollectionParams, List<MediaItem>>() {

    override suspend fun run(
        params: ListCollectionParams
    ): AppResult<AppFailure, List<MediaItem>> = try {

        listMediaItemsWithDetailUseCase {
            when (params.collection) {

                ListCollection.Watched -> showsRepository.watched(
                    period = params.period,
                    limit = params.limit
                )

                ListCollection.Recommended -> showsRepository.recommended(
                    period = params.period,
                    limit = params.limit
                )

                ListCollection.Collected -> showsRepository.collected(
                    period = params.period,
                    limit = params.limit
                )

                ListCollection.Popular -> showsRepository.popular(
                    period = params.period,
                    limit = params.limit
                )

                ListCollection.Anticipated -> showsRepository.anticipated(
                    period = params.period,
                    limit = params.limit
                )
            }
        }

    } catch (ex: Exception) {

        AppLogger.error(ex)
        AppResult.Failure(ListCollectionShowsError(ex))
    }

    data class ListCollectionShowsError(
        val error: Exception
    ) : AppFailure.FeatureFailure()
}