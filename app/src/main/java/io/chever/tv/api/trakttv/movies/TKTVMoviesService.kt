package io.chever.tv.api.trakttv.movies

import io.chever.tv.api.trakttv.domain.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * TODO: document service
 */
interface TKTVMoviesService {

    @GET("trending")
    suspend fun trending(
        @Query("limit") limit: Int = 10
    ): Response<List<TKMovieTrending>>

    @GET("popular")
    suspend fun popular(
        @Query("limit") limit: Int = 10
    ): Response<List<TKMovie>>

    @GET("recommended/{period}")
    suspend fun recommended(
        @Path("period") period: String,
        @Query("limit") limit: Int = 10
    ): Response<List<TKMovieRecommended>>

    @GET("watched/{period}")
    suspend fun watched(
        @Path("period") period: String,
        @Query("limit") limit: Int = 10
    ): Response<List<TKMoviePlayed>>

    @GET("played/{period}")
    suspend fun played(
        @Path("period") period: String,
        @Query("limit") limit: Int = 10
    ): Response<List<TKMoviePlayed>>

    @GET("collected/{period}")
    suspend fun collected(
        @Path("period") period: String,
        @Query("limit") limit: Int = 10
    ): Response<List<TKMoviePlayed>>

    @GET("anticipated")
    suspend fun anticipated(
        @Query("limit") limit: Int = 10
    ): Response<List<TKMovieAnticipated>>

    @GET("{id}/comments/{sort}")
    suspend fun comments(
        @Path("id") id: String,
        @Path("sort") sort: String,
    ): Response<List<TKComment>>

    @GET("{id}/related")
    suspend fun related(@Path("id") id: String): Response<List<TKMovie>>

    @GET("{id}/ratings")
    suspend fun ratings(@Path("id") id: String): Response<TKMovieRating>

    @GET("{id}/stats")
    suspend fun stats(@Path("id") id: String): Response<TKMovieStats>
}