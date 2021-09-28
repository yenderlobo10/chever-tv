package io.chever.tv.ui.movies.viewModel

import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger
import io.chever.tv.api.themoviedb.domain.enums.TMVideoType
import io.chever.tv.api.themoviedb.domain.models.TMMovieTitle
import io.chever.tv.api.themoviedb.domain.models.TMMovieTitlesResult
import io.chever.tv.api.themoviedb.domain.models.TMVideoResult
import io.chever.tv.api.themoviedb.domain.models.TMVideos
import io.chever.tv.common.extension.Result
import io.chever.tv.ui.movies.repository.MovieDetailRepository
import kotlinx.coroutines.flow.flow

class MovieDetailViewModel : ViewModel() {

    private val repository = MovieDetailRepository()

    private val filterCountries = arrayListOf("US", "ES", "MX")


    fun findMovieCredits(id: Long) = flow {

        try {

            emit(Result.Loading)

            emit(repository.credits(id))

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
            emit(Result.Error(ex.message, exception = ex))
        }
    }

    fun findMovieRecommendations(id: Long) = flow {

        try {

            emit(Result.Loading)

            emit(repository.recommendations(id))

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
            emit(Result.Error(ex.message, exception = ex))
        }
    }

    fun findMovieDetails(id: Long) = flow {

        try {

            emit(Result.Loading)

            emit(repository.details(id))

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
            emit(Result.Error(ex.message, exception = ex))
        }
    }


    fun findMovieTrailer(id: Long) = flow {

        try {

            emit(Result.Loading)

            val result = repository.videos(id)

            if (result is Result.Success)
                emit(tryFilterTrailerOfficialYouTubeVideo(result.data))
            else
                emit(Result.Empty)

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
            emit(Result.Error(ex.message, exception = ex))
        }
    }

    private fun tryFilterTrailerOfficialYouTubeVideo(
        tmVideos: TMVideos
    ): Result<TMVideoResult> {

        // TODO: check filter site constant?
        val ytVideos = tmVideos.results.filter { x ->
            x.type == TMVideoType.Trailer && x.site == "YouTube"
        }

        var ytVideo = ytVideos.firstOrNull { x -> x.isOfficial }

        if (ytVideo == null) ytVideo = ytVideos.firstOrNull()

        return if (ytVideo is TMVideoResult)
            Result.Success(ytVideo)
        else
            Result.Empty
    }


    fun findMovieAlternativeTitles(id: Long) = flow {

        try {

            emit(Result.Loading)

            val result = repository.alternativeTitles(id)

            if (result is Result.Success)
                emit(tryFilterAlternativeTitles(result.data))
            else
                emit(Result.Empty)

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
            emit(Result.Error(ex.message, exception = ex))
        }
    }

    private fun tryFilterAlternativeTitles(
        titles: TMMovieTitlesResult
    ): Result<List<TMMovieTitle>> {


        val filter = titles.titles.filter { x ->
            filterCountries.contains(x.country)
        }

        return if (filter.isNotEmpty())
            Result.Success(filter)
        else
            Result.Empty

    }
}