package io.chever.domain.usecase.media

import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.model.media.MediaItem
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import io.chever.domain.usecase.base.UseCaseParams
import io.chever.domain.usecase.movie.GetMovieRecommendationsUseCase
import io.chever.domain.usecase.show.GetShowRecommendationsUseCase
import javax.inject.Inject

class GetMediaItemRecommendationsUseCase @Inject constructor(
    private val getMovieRecommendationsUseCase: GetMovieRecommendationsUseCase,
    private val getShowRecommendationsUseCase: GetShowRecommendationsUseCase
) : UseCaseParams<GetMediaItemRecommendationsUseCase.Params, List<MediaItem>>() {

    override suspend fun run(
        params: Params
    ): AppResult<AppFailure, List<MediaItem>> = when (params.mediaType) {

        MediaTypeEnum.Movie -> getMovieRecommendationsUseCase(id = params.id)

        MediaTypeEnum.Show -> getShowRecommendationsUseCase(id = params.id)

        else -> AppResult.Failure(
            GetMediaItemRecommendationsFailure(error = "Invalid @mediaType param")
        )
    }

    data class Params(
        val id: Long,
        val mediaType: MediaTypeEnum
    )

    data class GetMediaItemRecommendationsFailure(
        val error: String
    ) : AppFailure.FeatureFailure()
}