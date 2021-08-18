package io.chever.tv.ui.movies.viewModel

import android.annotation.SuppressLint
import android.content.Context
import android.util.SparseArray
import androidx.core.util.keyIterator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.orhanobut.logger.Logger
import io.chever.tv.api.themoviedb.domain.enums.TMVideoType
import io.chever.tv.api.themoviedb.domain.models.TMMovieCredits
import io.chever.tv.api.themoviedb.domain.models.TMMoviesRecommended
import io.chever.tv.api.themoviedb.domain.models.TMVideoResult
import io.chever.tv.api.themoviedb.domain.models.TMVideos
import io.chever.tv.common.extension.Result
import io.chever.tv.common.extension.Util
import io.chever.tv.ui.movies.repository.MovieDetailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.job

class MovieDetailViewModel : ViewModel() {

    private val repository = MovieDetailRepository()

    private val movieCredits = MutableStateFlow<TMMovieCredits?>(null)
    private val listMoviesRelated = MutableStateFlow<TMMoviesRecommended?>(null)


    fun findMovieCredits(id: Long) = flow {

        try {

            emit(Result.Loading)

            if (movieCredits.value is TMMovieCredits)
                emit(Result.Success(movieCredits.value))
            else
                emit(repository.credits(id))


        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
            emit(Result.Error(ex.message, exception = ex))
        }
    }

    fun findMovieRecommendations(id: Long) = flow {

        try {

            emit(Result.Loading)

            if (listMoviesRelated.value is TMMoviesRecommended)
                emit(Result.Success(listMoviesRelated.value))
            else
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

}