package io.chever.tv.ui.movies.repository

import io.chever.tv.api.themoviedb.TheMovieDB
import io.chever.tv.common.extension.AppBaseRepository

class MovieDetailRepository : AppBaseRepository() {

    suspend fun credits(id: Long) = getResult {
        TheMovieDB.movies.credits(id)
    }

    suspend fun recommendations(id: Long) = getResult {
        TheMovieDB.movies.recommendations(id)
    }

    suspend fun details(id: Long) = getResult {
        TheMovieDB.movies.details(id)
    }

    suspend fun videos(id: Long, language: String = "") = getResult {
        TheMovieDB.movies.videos(id, language)
    }

    suspend fun alternativeTitles(id: Long) = getResult {
        TheMovieDB.movies.alternativeTitle(id)
    }
}