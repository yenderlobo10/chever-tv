package io.chever.domain.usecase.show

import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import io.chever.domain.model.show.ShowDetail
import io.chever.domain.repository.ShowRepository
import io.chever.shared.observability.AppLogger
import javax.inject.Inject

class GetShowDetailUseCase @Inject constructor(
    private val showRepository: ShowRepository
) {

    suspend operator fun invoke(
        id: Long
    ): AppResult<AppFailure, ShowDetail> {

        return try {

            showRepository.details(id)

        } catch (ex: Exception) {

            AppLogger.error(ex)
            AppResult.Failure(GetShowDetailFailure(ex))
        }
    }

    data class GetShowDetailFailure(
        val error: Throwable
    ) : AppFailure.FeatureFailure()
}