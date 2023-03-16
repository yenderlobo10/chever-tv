package io.chever.domain.usecase.media

import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.model.detail.PersonCast
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import io.chever.domain.usecase.base.UseCaseParams
import io.chever.domain.usecase.movie.GetMovieCreditsUseCase
import io.chever.domain.usecase.show.GetShowCreditsUseCase
import javax.inject.Inject

class GetMediaItemCreditsUseCase @Inject constructor(
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    private val getShowCreditsUseCase: GetShowCreditsUseCase
) : UseCaseParams<GetMediaItemCreditsUseCase.Params, List<PersonCast>>() {

    override suspend fun run(
        params: Params
    ): AppResult<AppFailure, List<PersonCast>> = when (params.mediaType) {

        MediaTypeEnum.Movie -> getMovieCreditsUseCase(id = params.id)

        MediaTypeEnum.Show -> getShowCreditsUseCase(id = params.id)

        else -> AppResult.Failure(
            GetMediaItemCreditsFailure(error = "Invalid @mediaType param")
        )
    }


    data class Params(
        val id: Long,
        val mediaType: MediaTypeEnum
    )

    data class GetMediaItemCreditsFailure(
        val error: String
    ) : AppFailure.FeatureFailure()
}