package io.chever.data.api.themoviedb.service

import io.chever.data.api.themoviedb.model.TMMovie
import io.chever.data.api.themoviedb.model.TMMovieCredits
import io.chever.data.api.themoviedb.model.TMMovieDetail
import io.chever.data.api.themoviedb.model.TMMovieTitlesResult
import io.chever.data.api.themoviedb.model.TMObjectListResult
import io.chever.data.api.themoviedb.model.TMVideosResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * TODO: document interface service
 */
interface TMDBMoviesService {

    @GET("movie/{movie_id}")
    suspend fun details(
        @Path("movie_id") id: Long
    ): Response<TMMovieDetail>

    @GET("movie/{movie_id}/credits")
    suspend fun credits(
        @Path("movie_id") id: Long
    ): Response<TMMovieCredits>

    @GET("movie/{movie_id}/recommendations")
    suspend fun recommendations(
        @Path("movie_id") id: Long
    ): Response<TMObjectListResult<TMMovie>>

    @GET("movie/{movie_id}/similar")
    suspend fun similar(
        @Path("movie_id") id: Long
    ): Response<TMObjectListResult<TMMovie>>

    @GET("movie/{movie_id}/videos")
    suspend fun videos(
        @Path("movie_id") id: Long,
        @Query("language") lg: String
    ): Response<TMVideosResult>

    @GET("movie/{movie_id}/alternative_titles")
    suspend fun alternativeTitle(
        @Path("movie_id") id: Long
    ): Response<TMMovieTitlesResult>
}