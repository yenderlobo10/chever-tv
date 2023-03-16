package io.chever.data.api.themoviedb.service

import io.chever.data.api.themoviedb.model.TMCollectionResponse
import io.chever.data.api.themoviedb.model.TMCreditsResponse
import io.chever.data.api.themoviedb.model.show.TMShowDetailResponse
import io.chever.data.api.themoviedb.model.show.TMShowResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBShowsService {

    @GET("tv/{tv_id}")
    suspend fun details(
        @Path("tv_id") id: Long,
        @Query("append_to_response") append: String = ""
    ): Response<TMShowDetailResponse>

    @GET("tv/{tv_id}/credits")
    suspend fun credits(
        @Path("tv_id") id: Long
    ): Response<TMCreditsResponse>

    @GET("tv/{tv_id}/recommendations")
    suspend fun recommendations(
        @Path("tv_id") id: Long
    ): Response<TMCollectionResponse<TMShowResponse>>
}