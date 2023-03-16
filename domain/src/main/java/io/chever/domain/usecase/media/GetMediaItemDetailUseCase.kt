package io.chever.domain.usecase.media

import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.model.media.MediaItemDetail
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import io.chever.domain.usecase.base.UseCaseParams
import io.chever.domain.usecase.movie.GetMovieDetailUseCase
import io.chever.domain.usecase.show.GetShowDetailUseCase
import javax.inject.Inject

class GetMediaItemDetailUseCase @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getShowDetailUseCase: GetShowDetailUseCase
) : UseCaseParams<GetMediaItemDetailUseCase.Params, MediaItemDetail>() {

    override suspend fun run(
        params: Params
    ): AppResult<AppFailure, MediaItemDetail> = when (params.mediaType) {

        MediaTypeEnum.Movie -> getMovieDetail(params.id)

        MediaTypeEnum.Show -> getShowDetail(params.id)

        else -> AppResult.Success(MediaItemDetail.None)
    }


    private suspend fun getMovieDetail(
        id: Long
    ): AppResult<AppFailure, MediaItemDetail> = when (
        val response = getMovieDetailUseCase(id)
    ) {
        is AppResult.Success -> AppResult.Success(
            MediaItemDetail.Movie(detail = response.value)
        )
        is AppResult.Failure -> AppResult.Failure(response.value)
    }

    private suspend fun getShowDetail(
        id: Long
    ): AppResult<AppFailure, MediaItemDetail> = when (
        val response = getShowDetailUseCase(id)
    ) {
        is AppResult.Success -> AppResult.Success(
            MediaItemDetail.Show(detail = response.value)
        )
        is AppResult.Failure -> AppResult.Failure(response.value)
    }


    data class Params(
        val id: Long,
        val mediaType: MediaTypeEnum
    )
}