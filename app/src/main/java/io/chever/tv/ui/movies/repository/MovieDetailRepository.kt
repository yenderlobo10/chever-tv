package io.chever.tv.ui.movies.repository

import io.chever.tv.api.themoviedb.TheMovieDB
import io.chever.tv.common.extension.BaseRepository

class MovieDetailRepository : BaseRepository() {

    suspend fun credits(id: Long) = getResult {
        TheMovieDB.movies.credits(id)
    }

    suspend fun recommendations(id: Long) = getResult {
        TheMovieDB.movies.recommendations(id)
    }

    suspend fun details(id: Long) = getResult {
        TheMovieDB.movies.details(id)
    }
}