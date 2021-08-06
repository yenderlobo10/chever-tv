package io.chever.tv.ui.movies.viewModel

import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger
import io.chever.tv.api.themoviedb.domain.models.TMMovieCredits
import io.chever.tv.api.themoviedb.domain.models.TMMoviesRecommended
import io.chever.tv.common.extension.Result
import io.chever.tv.ui.movies.repository.MovieDetailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

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
}