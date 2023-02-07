package io.chever.data.api.themoviedb.service

import io.chever.data.api.themoviedb.enums.TMMediaTypeEnum
import io.chever.data.api.themoviedb.model.TMCollectionResponse
import io.chever.data.api.themoviedb.model.TMTrendingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TMDBTrendingService {

    @GET("trending/{media_type}/{time_window}")
    suspend fun trending(
        @Path("media_type") mediaType: String = TMMediaTypeEnum.All.value,
        @Path("time_window") timeWindow: String
    ): Response<TMCollectionResponse<TMTrendingResponse>>
}