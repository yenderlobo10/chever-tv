package io.chever.domain.usecase.show

import io.chever.domain.model.detail.PersonCast
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import io.chever.domain.repository.ShowRepository
import io.chever.shared.observability.AppLogger
import javax.inject.Inject

class GetShowCreditsUseCase @Inject constructor(
    private val showRepository: ShowRepository
) {

    suspend operator fun invoke(
        id: Long
    ): AppResult<AppFailure, List<PersonCast>> = try {

        showRepository.credits(id)

    } catch (ex: Exception) {

        AppLogger.error(ex)
        AppResult.Failure(GetShowCreditsFailure(ex))
    }

    data class GetShowCreditsFailure(
        val error: Throwable
    ) : AppFailure.FeatureFailure()
}