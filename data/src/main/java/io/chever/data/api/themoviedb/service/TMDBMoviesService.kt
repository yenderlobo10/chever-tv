package io.chever.data.api.themoviedb.service

import io.chever.data.api.themoviedb.model.TMCollectionResponse
import io.chever.data.api.themoviedb.model.TMVideosResponse
import io.chever.data.api.themoviedb.model.movies.TMMovieCreditsResponse
import io.chever.data.api.themoviedb.model.movies.TMMovieDetailResponse
import io.chever.data.api.themoviedb.model.movies.TMMovieResponse
import io.chever.data.api.themoviedb.model.movies.TMMovieTitlesResponse
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
        @Path("movie_id") id: Long,
        @Query("append_to_response") append: String = ""
    ): Response<TMMovieDetailResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun credits(
        @Path("movie_id") id: Long
    ): Response<TMMovieCreditsResponse>

    @GET("movie/{movie_id}/recommendations")
    suspend fun recommendations(
        @Path("movie_id") id: Long
    ): Response<TMCollectionResponse<TMMovieResponse>>

    @GET("movie/{movie_id}/similar")
    suspend fun similar(
        @Path("movie_id") id: Long
    ): Response<TMCollectionResponse<TMMovieResponse>>

    @GET("movie/{movie_id}/videos")
    suspend fun videos(
        @Path("movie_id") id: Long,
        @Query("language") lg: String
    ): Response<TMVideosResponse>

    @GET("movie/{movie_id}/alternative_titles")
    suspend fun alternativeTitle(
        @Path("movie_id") id: Long
    ): Response<TMMovieTitlesResponse>
}