package io.chever.data.api.trakttv.service

import io.chever.data.api.trakttv.enums.TKExtendedInfo
import io.chever.data.api.trakttv.model.TKComment
import io.chever.data.api.trakttv.model.TKMovie
import io.chever.data.api.trakttv.model.TKMovieAnticipatedResult
import io.chever.data.api.trakttv.model.TKMoviePlayedResult
import io.chever.data.api.trakttv.model.TKMovieRating
import io.chever.data.api.trakttv.model.TKMovieRecommendedResult
import io.chever.data.api.trakttv.model.TKMovieStats
import io.chever.data.api.trakttv.model.TKMovieTrendingResult
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

    @GET("trending")
    suspend fun trending(
        @Query("limit") limit: Int = 10,
        @Query("extended") extended: String = TKExtendedInfo.Full.value
    ): Response<List<TKMovieTrendingResult>>

    @GET("popular")
    suspend fun popular(
        @Query("limit") limit: Int = 10,
        @Query("extended") extended: String = TKExtendedInfo.Full.value
    ): Response<List<TKMovie>>

    @GET("recommended/{period}")
    suspend fun recommended(
        @Path("period") period: String,
        @Query("limit") limit: Int = 10,
        @Query("extended") extended: String = TKExtendedInfo.Full.value
    ): Response<List<TKMovieRecommendedResult>>

    @GET("watched/{period}")
    suspend fun watched(
        @Path("period") period: String,
        @Query("limit") limit: Int = 10,
        @Query("extended") extended: String = TKExtendedInfo.Full.value
    ): Response<List<TKMoviePlayedResult>>

    @GET("played/{period}")
    suspend fun played(
        @Path("period") period: String,
        @Query("limit") limit: Int = 10,
        @Query("extended") extended: String = TKExtendedInfo.Full.value
    ): Response<List<TKMoviePlayedResult>>

    @GET("collected/{period}")
    suspend fun collected(
        @Path("period") period: String,
        @Query("limit") limit: Int = 10,
        @Query("extended") extended: String = TKExtendedInfo.Full.value
    ): Response<List<TKMoviePlayedResult>>

    @GET("anticipated")
    suspend fun anticipated(
        @Query("limit") limit: Int = 10,
        @Query("extended") extended: String = TKExtendedInfo.Full.value
    ): Response<List<TKMovieAnticipatedResult>>

    @GET("{id}/comments/{sort}")
    suspend fun comments(
        @Path("id") id: String,
        @Path("sort") sort: String,
    ): Response<List<TKComment>>

    @GET("{id}/related")
    suspend fun related(
        @Path("id") id: String,
        @Query("extended") extended: String = TKExtendedInfo.Full.value
    ): Response<List<TKMovie>>

    @GET("{id}/ratings")
    suspend fun ratings(
        @Path("id") id: String
    ): Response<TKMovieRating>

    @GET("{id}/stats")
    suspend fun stats(
        @Path("id") id: String
    ): Response<TKMovieStats>
}