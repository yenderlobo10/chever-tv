package io.chever.tv.ui.movies.repository

import io.chever.tv.api.themoviedb.TheMovieDB
import io.chever.tv.api.trakttv.TraktTV
import io.chever.tv.api.trakttv.domain.enums.TKPeriod
import io.chever.tv.common.extension.AppBaseRepository

class MoviesBrowseRepository : AppBaseRepository() {

    suspend fun trending(
        limit: Int = 10
    ) = getResult {
        TraktTV.movies.trending(limit = limit)
    }

    suspend fun recommended(
        period: TKPeriod = TKPeriod.Weekly,
        limit: Int = 10
    ) = getResult {
        TraktTV.movies.recommended(
            period = period.value,
            limit = limit
        )
    }

    suspend fun played(
        period: TKPeriod = TKPeriod.Weekly,
        limit: Int = 10
    ) = getResult {
        TraktTV.movies.played(
            period = period.value,
            limit = limit
        )
    }

    suspend fun collected(
        period: TKPeriod = TKPeriod.Weekly,
        limit: Int = 10
    ) = getResult {
        TraktTV.movies.collected(
            period = period.value,
            limit = limit
        )
    }

    suspend fun popular(
        limit: Int = 10
    ) = getResult {
        TraktTV.movies.popular(limit = limit)
    }

    suspend fun anticipated(
        limit: Int = 10
    ) = getResult {
        TraktTV.movies.anticipated(limit = limit)
    }


    suspend fun tmMovieDetail(id: Long) = getResult {
        TheMovieDB.movies.details(id)
    }
}