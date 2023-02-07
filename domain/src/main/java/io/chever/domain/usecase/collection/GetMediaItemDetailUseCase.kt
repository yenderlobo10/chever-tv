package io.chever.domain.usecase.collection

import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.model.collection.MediaItemDetail
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import io.chever.domain.repository.MovieRepository
import io.chever.domain.repository.ShowRepository
import io.chever.domain.usecase.base.UseCaseParams
import javax.inject.Inject

class GetMediaItemDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val showRepository: ShowRepository
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
        val response = movieRepository.details(id)
    ) {
        is AppResult.Success -> AppResult.Success(
            MediaItemDetail.Movie(detail = response.value)
        )
        is AppResult.Failure -> AppResult.Failure(response.value)
    }

    private suspend fun getShowDetail(
        id: Long
    ): AppResult<AppFailure, MediaItemDetail> = when (
        val response = showRepository.details(id)
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