package io.chever.tv.ui.movies.viewModel

import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger
import io.chever.tv.api.themoviedb.domain.models.TMMovieDetail
import io.chever.tv.api.trakttv.domain.models.TKMovie
import io.chever.tv.common.extension.Result
import io.chever.tv.common.extension.Util
import io.chever.tv.common.models.MovieCardItem
import io.chever.tv.ui.movies.MoviesBrowseRepository
import io.chever.tv.ui.movies.common.enums.MovieCollection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class MoviesBrowseViewModel : ViewModel() {

    private val repository = MoviesBrowseRepository()

    private val moviesByCollection = MutableStateFlow<MutableList<MovieCardItem>>(mutableListOf())

    init {

    }


    fun findMoviesByCollections() = flow {

        try {

            emit(Result.Loading)

            when {

                // Emit saved movies collection
                moviesByCollection.value.isNotEmpty() ->
                    emit(Result.Success(moviesByCollection.value))

                else -> {

                    // Get movies by collection
                    MovieCollection.values().forEach { collection ->

                        val result = getMoviesByCollection(collection)

                        if (result is Result.Success)
                            moviesByCollection.value.addAll(result.data)
                        else
                            Logger.w(result.toString())
                    }

                    // Check results
                    if (moviesByCollection.value.isNotEmpty())
                        emit(Result.Success(moviesByCollection.value))
                    else
                        emit(Result.Error("Not found results"))
                }
            }

        } catch (ex: Exception) {

            Logger.e(ex, ex.message!!)

            emit(Result.Error(ex.message, exception = ex))
        }
    }


    private suspend fun getMoviesByCollection(collection: MovieCollection): Result<List<MovieCardItem>> {

        return when (collection) {

            MovieCollection.Trending -> getMoviesTrending()

            MovieCollection.Recommended -> getMoviesRecommended()

            MovieCollection.Played -> getMoviesPlayed()

            MovieCollection.Collected -> getMoviesCollected()

            MovieCollection.Popular -> getMoviesPopular()

            MovieCollection.Anticipated -> getMoviesAnticipated()
        }
    }

    private suspend fun getMoviesTrending(): Result<List<MovieCardItem>> {

        val result = repository.trending()

        if (result is Result.Success) {

            val listTKMovies = result.data.map { x -> x.movie }

            val movieCardItems = createListCardItemsByTKMovies(
                tkMovies = listTKMovies,
                collection = MovieCollection.Trending
            ).mapIndexed { i, item ->

                val trending = result.data[i]
                // TODO: use res-string or translate
                item.chips.add("${trending.watchers} personas viendo")

                return@mapIndexed item
            }

            return Result.Success(movieCardItems)
        }

        return Result.Error(message = "Error loading @Trending movies")
    }

    private suspend fun getMoviesRecommended(): Result<List<MovieCardItem>> {

        val result = repository.recommended()

        if (result is Result.Success) {

            val listTKMovies = result.data.map { x -> x.movie }

            val movieCardItems = createListCardItemsByTKMovies(
                tkMovies = listTKMovies,
                collection = MovieCollection.Recommended
            ).mapIndexed { i, item ->

                val recommended = result.data[i]
                // TODO: use res-string or translate
                item.chips.add("${recommended.userCount} personas recomiendan")

                return@mapIndexed item
            }

            return Result.Success(movieCardItems)
        }

        return Result.Error(message = "Error loading @Recommended movies")
    }

    private suspend fun getMoviesPlayed(): Result<List<MovieCardItem>> {

        val result = repository.played()

        if (result is Result.Success) {

            val listTKMovies = result.data.map { x -> x.movie }

            val movieCardItems = createListCardItemsByTKMovies(
                tkMovies = listTKMovies,
                collection = MovieCollection.Played
            ).mapIndexed { i, item ->

                val played = result.data[i]
                // TODO: use res-string or translate
                item.chips.add("${played.watcherCount} visitas")
                item.chips.add("${played.playCount} reproducciones")

                return@mapIndexed item
            }

            return Result.Success(movieCardItems)
        }

        return Result.Error(message = "Error loading @Played movies")
    }

    private suspend fun getMoviesCollected(): Result<List<MovieCardItem>> {

        val result = repository.collected()

        if (result is Result.Success) {

            val listTKMovies = result.data.map { x -> x.movie }

            val movieCardItems = createListCardItemsByTKMovies(
                tkMovies = listTKMovies,
                collection = MovieCollection.Collected
            ).mapIndexed { i, item ->

                val collected = result.data[i]
                // TODO: use res-string or translate
                item.chips.add("${collected.collectedCount} colecciones")

                return@mapIndexed item
            }

            return Result.Success(movieCardItems)
        }

        return Result.Error(message = "Error loading @Collected movies")
    }

    private suspend fun getMoviesPopular(): Result<List<MovieCardItem>> {

        val result = repository.popular()

        if (result is Result.Success) {

            val listTKMovies = result.data

            val movieCardItems = createListCardItemsByTKMovies(
                tkMovies = listTKMovies,
                collection = MovieCollection.Popular
            )

            return Result.Success(movieCardItems)
        }

        return Result.Error(message = "Error loading @Popular movies")
    }

    private suspend fun getMoviesAnticipated(): Result<List<MovieCardItem>> {

        val result = repository.anticipated()

        if (result is Result.Success) {

            val listTKMovies = result.data.map { x -> x.movie }

            val movieCardItems = createListCardItemsByTKMovies(
                tkMovies = listTKMovies,
                collection = MovieCollection.Anticipated
            ).mapIndexed { i, item ->

                val anticipated = result.data[i]
                // TODO: use res-string or translate
                item.chips.add("${anticipated.listCount} listas")
                item.chips.add("${item.releaseDate}")

                return@mapIndexed item
            }

            return Result.Success(movieCardItems)
        }

        return Result.Error(message = "Error loading @Anticipated movies")
    }


    private suspend fun createListCardItemsByTKMovies(
        tkMovies: List<TKMovie>,
        collection: MovieCollection
    ): List<MovieCardItem> {

        val movieCardItems = mutableListOf<MovieCardItem>()

        tkMovies.forEach {

            // Create card-item =>
            val cardItem = MovieCardItem(
                title = it.title,
                year = it.year,
                collection = collection,
            ).apply {

                idTKTV = it.ids.trakt
                idTMDB = it.ids.tmdb
            }

            // Get movie-detail & set card-item =>
            getTMMovieDetail(it.ids.tmdb)?.let { detail ->

                cardItem.transTitle = detail.title
                cardItem.backdropUrl = Util.createTMDbImageUrl(detail.backdropPath)
                cardItem.releaseDate = detail.releaseDate?.toString()
            }

            movieCardItems.add(cardItem)
        }

        return movieCardItems
    }


    private suspend fun getTMMovieDetail(movieId: Long): TMMovieDetail? {

        return when (val result = repository.tmMovieDetail(movieId)) {

            is Result.Success -> result.data

            else -> null
        }
    }
}