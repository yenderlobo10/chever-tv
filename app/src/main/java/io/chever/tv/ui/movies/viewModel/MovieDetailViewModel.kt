package io.chever.tv.ui.movies.viewModel

import androidx.lifecycle.ViewModel
import io.chever.tv.api.themoviedb.domain.enums.TMVideoType
import io.chever.tv.api.themoviedb.domain.models.TMMovieTitle
import io.chever.tv.api.themoviedb.domain.models.TMMovieTitlesResult
import io.chever.tv.api.themoviedb.domain.models.TMVideoResult
import io.chever.tv.api.themoviedb.domain.models.TMVideos
import io.chever.tv.common.extension.AppResult
import io.chever.tv.ui.movies.repository.MovieDetailRepository
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class MovieDetailViewModel : ViewModel() {

    private val repository = MovieDetailRepository()

    private val filterCountries = arrayListOf("US", "ES", "MX")


    fun findMovieCredits(id: Long) = flow {

        try {

            emit(AppResult.Loading)

            emit(repository.credits(id))

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
            emit(AppResult.Error(ex.message, exception = ex))
        }
    }

    fun findMovieRecommendations(id: Long) = flow {

        try {

            emit(AppResult.Loading)

            emit(repository.recommendations(id))

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
            emit(AppResult.Error(ex.message, exception = ex))
        }
    }

    fun findMovieDetails(id: Long) = flow {

        try {

            emit(AppResult.Loading)

            emit(repository.details(id))

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
            emit(AppResult.Error(ex.message, exception = ex))
        }
    }


    fun findMovieTrailer(id: Long) = flow {

        try {

            emit(AppResult.Loading)

            val result = repository.videos(id)

            if (result is AppResult.Success)
                emit(tryFilterTrailerOfficialYouTubeVideo(result.data))
            else
                emit(AppResult.Empty)

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
            emit(AppResult.Error(ex.message, exception = ex))
        }
    }

    private fun tryFilterTrailerOfficialYouTubeVideo(
        tmVideos: TMVideos
    ): AppResult<TMVideoResult> {

        // TODO: check filter site constant?
        val ytVideos = tmVideos.results.filter { x ->
            x.type == TMVideoType.Trailer && x.site == "YouTube"
        }

        var ytVideo = ytVideos.firstOrNull { x -> x.isOfficial }

        if (ytVideo == null) ytVideo = ytVideos.firstOrNull()

        return if (ytVideo is TMVideoResult)
            AppResult.Success(ytVideo)
        else
            AppResult.Empty
    }


    fun findMovieAlternativeTitles(id: Long) = flow {

        try {

            emit(AppResult.Loading)

            val result = repository.alternativeTitles(id)

            if (result is AppResult.Success)
                emit(tryFilterAlternativeTitles(result.data))
            else
                emit(AppResult.Empty)

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
            emit(AppResult.Error(ex.message, exception = ex))
        }
    }

    private fun tryFilterAlternativeTitles(
        titles: TMMovieTitlesResult
    ): AppResult<List<TMMovieTitle>> {


        val filter = titles.titles.filter { x ->
            filterCountries.contains(x.country)
        }

        return if (filter.isNotEmpty())
            AppResult.Success(filter)
        else
            AppResult.Empty

    }
}