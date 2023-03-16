package io.chever.data.api.trakttv.service

import io.chever.data.api.trakttv.enums.TKPeriodEnum
import io.chever.data.api.trakttv.model.show.TKShowResponse
import io.chever.data.api.trakttv.model.show.TKShowsAnticipatedResponse
import io.chever.data.api.trakttv.model.show.TKShowsCollectionResponse
import io.chever.data.api.trakttv.model.show.TKShowsRecommendedResponse
import io.chever.data.api.trakttv.model.show.TKShowsTrendingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Trakt.tv **shows** service end-points.
 *
 * @see <a href="https://trakt.docs.apiary.io/reference/shows">Shows docs</a>
 */
@Suppress("unused")
interface TKTVShowsService {

    @GET("shows/trending")
    suspend fun trending(
        @Query("limit") limit: Int = 10
    ): Response<List<TKShowsTrendingResponse>>

    @GET("shows/popular")
    suspend fun popular(
        @Query("limit") limit: Int = 10
    ): Response<List<TKShowResponse>>

    @GET("shows/recommended/{period}")
    suspend fun recommended(
        @Path("period") period: String = TKPeriodEnum.Weekly.value,
        @Query("limit") limit: Int = 10
    ): Response<List<TKShowsRecommendedResponse>>

    @GET("shows/watched/{period}")
    suspend fun watched(
        @Path("period") period: String = TKPeriodEnum.Weekly.value,
        @Query("limit") limit: Int = 10
    ): Response<List<TKShowsCollectionResponse>>

    @GET("shows/played/{period}")
    suspend fun played(
        @Path("period") period: String = TKPeriodEnum.Weekly.value,
        @Query("limit") limit: Int = 10
    ): Response<List<TKShowsCollectionResponse>>

    @GET("shows/collected/{period}")
    suspend fun collected(
        @Path("period") period: String = TKPeriodEnum.Weekly.value,
        @Query("limit") limit: Int = 10
    ): Response<List<TKShowsCollectionResponse>>

    @GET("shows/anticipated")
    suspend fun anticipated(
        @Query("limit") limit: Int = 10
    ): Response<List<TKShowsAnticipatedResponse>>
}