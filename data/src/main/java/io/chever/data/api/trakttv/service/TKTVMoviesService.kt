package io.chever.data.api.trakttv.service

import io.chever.data.api.trakttv.enums.TKPeriodEnum
import io.chever.data.api.trakttv.model.TKCommentResponse
import io.chever.data.api.trakttv.model.movies.TKMovieRatingResponse
import io.chever.data.api.trakttv.model.movies.TKMovieResponse
import io.chever.data.api.trakttv.model.movies.TKMovieStatsResponse
import io.chever.data.api.trakttv.model.movies.TKMoviesAnticipatedResponse
import io.chever.data.api.trakttv.model.movies.TKMoviesCollectionResponse
import io.chever.data.api.trakttv.model.movies.TKMoviesRecommendedResponse
import io.chever.data.api.trakttv.model.movies.TKMoviesTrendingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Trakt.tv **movies** service end-points.
 *
 * @see <a href="https://trakt.docs.apiary.io/reference/movies">Movies docs</a>
 */
@Suppress("unused")
interface TKTVMoviesService {

    @GET("movies/trending")
    suspend fun trending(
        @Query("limit") limit: Int = 10
    ): Response<List<TKMoviesTrendingResponse>>

    @GET("movies/popular")
    suspend fun popular(
        @Query("limit") limit: Int = 10
    ): Response<List<TKMovieResponse>>

    @GET("movies/recommended/{period}")
    suspend fun recommended(
        @Path("period") period: String = TKPeriodEnum.Weekly.value,
        @Query("limit") limit: Int = 10
    ): Response<List<TKMoviesRecommendedResponse>>

    @GET("movies/watched/{period}")
    suspend fun watched(
        @Path("period") period: String = TKPeriodEnum.Weekly.value,
        @Query("limit") limit: Int = 10
    ): Response<List<TKMoviesCollectionResponse>>

    @GET("movies/played/{period}")
    suspend fun played(
        @Path("period") period: String = TKPeriodEnum.Weekly.value,
        @Query("limit") limit: Int = 10
    ): Response<List<TKMoviesCollectionResponse>>

    @GET("movies/collected/{period}")
    suspend fun collected(
        @Path("period") period: String = TKPeriodEnum.Weekly.value,
        @Query("limit") limit: Int = 10
    ): Response<List<TKMoviesCollectionResponse>>

    @GET("movies/anticipated")
    suspend fun anticipated(
        @Query("limit") limit: Int = 10
    ): Response<List<TKMoviesAnticipatedResponse>>

    @GET("movies/{id}/comments/{sort}")
    suspend fun comments(
        @Path("id") id: String,
        @Path("sort") sort: String,
    ): Response<List<TKCommentResponse>>

    @GET("movies/{id}/related")
    suspend fun related(
        @Path("id") id: String
    ): Response<List<TKMovieResponse>>

    @GET("movies/{id}/ratings")
    suspend fun ratings(
        @Path("id") id: String
    ): Response<TKMovieRatingResponse>

    @GET("movies/{id}/stats")
    suspend fun stats(
        @Path("id") id: String
    ): Response<TKMovieStatsResponse>
}