package io.chever.data.api.themoviedb.service

import io.chever.data.api.themoviedb.model.shows.TMShowDetailResponse
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
}