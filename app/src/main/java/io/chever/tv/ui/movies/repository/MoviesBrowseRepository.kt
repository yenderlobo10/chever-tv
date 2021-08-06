package io.chever.tv.ui.movies.repository

import io.chever.tv.api.themoviedb.TheMovieDB
import io.chever.tv.api.trakttv.TraktTV
import io.chever.tv.api.trakttv.domain.enums.TKPeriod
import io.chever.tv.common.extension.BaseRepository

class MoviesBrowseRepository : BaseRepository() {

    suspend fun trending() = getResult {
        TraktTV.movies.trending()
    }

    suspend fun recommended(period: TKPeriod = TKPeriod.Weekly) = getResult {
        TraktTV.movies.recommended(period = period.value)
    }

    suspend fun played(period: TKPeriod = TKPeriod.Weekly) = getResult {
        TraktTV.movies.played(period = period.value)
    }

    suspend fun collected(period: TKPeriod = TKPeriod.Weekly) = getResult {
        TraktTV.movies.collected(period = period.value)
    }

    suspend fun popular() = getResult {
        TraktTV.movies.popular()
    }

    suspend fun anticipated() = getResult {
        TraktTV.movies.anticipated()
    }


    suspend fun tmMovieDetail(id: Long) = getResult {
        TheMovieDB.movies.details(id)
    }
}