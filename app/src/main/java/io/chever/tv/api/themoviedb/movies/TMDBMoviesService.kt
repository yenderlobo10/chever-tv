package io.chever.tv.api.themoviedb.movies

import io.chever.tv.api.themoviedb.domain.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * TODO: document interface service
 */
interface TMDBMoviesService {

    @GET("{movie_id}")
    suspend fun details(
        @Path("movie_id") id: Long
    ): Response<TMMovieDetail>

    @GET("{movie_id}/credits")
    suspend fun credits(
        @Path("movie_id") id: Long
    ): Response<TMMovieCredits>

    @GET("{movie_id}/recommendations")
    suspend fun recommendations(
        @Path("movie_id") id: Long
    ): Response<TMMoviesRecommended>

    @GET("{movie_id}/similar")
    suspend fun similar(
        @Path("movie_id") id: Long
    ): Response<TMMoviesSimilar>

    @GET("{movie_id}/videos")
    suspend fun videos(
        @Path("movie_id") id: Long,
        @Query("language") lg: String
    ): Response<TMVideos>

    @GET("{movie_id}/alternative_titles")
    suspend fun alternativeTitle(
        @Path("movie_id") id: Long
    ): Response<TMMovieTitlesResult>
}