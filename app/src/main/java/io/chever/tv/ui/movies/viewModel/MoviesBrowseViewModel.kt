package io.chever.tv.ui.movies.viewModel

import androidx.lifecycle.ViewModel
import io.chever.tv.api.themoviedb.domain.models.TMMovieDetail
import io.chever.tv.api.trakttv.domain.models.TKMovie
import io.chever.tv.common.extension.DateTimeExtensions
import io.chever.tv.common.extension.DateTimeExtensions.toFormat
import io.chever.tv.common.extension.NumberExtensions.toFormat
import io.chever.tv.common.extension.AppResult
import io.chever.tv.common.extension.StringExtensions.capitalize
import io.chever.tv.ui.common.models.MovieCardItem
import io.chever.tv.ui.movies.common.enums.MovieCollection
import io.chever.tv.ui.movies.repository.MoviesBrowseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import org.jetbrains.annotations.TestOnly
import timber.log.Timber

class MoviesBrowseViewModel : ViewModel() {

    private val repository = MoviesBrowseRepository()

    private val moviesByCollection = MutableStateFlow<MutableList<MovieCardItem>>(mutableListOf())


    fun findMoviesByCollections() = flow {

        try {

            emit(AppResult.Loading)

            when {

                // Emit saved movies collection
                moviesByCollection.value.isNotEmpty() ->
                    emit(AppResult.Success(moviesByCollection.value))

                else -> {

                    // Get movies by collection
                    MovieCollection.values().forEach { collection ->

                        val result = getMoviesByCollection(collection)

                        if (result is AppResult.Success)
                            moviesByCollection.value.addAll(result.data)
                        else
                            Timber.w(result.toString())
                    }

                    // Check results
                    if (moviesByCollection.value.isNotEmpty())
                        emit(AppResult.Success(moviesByCollection.value))
                    else
                        emit(AppResult.Error("Not found results"))
                }
            }

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)

            emit(AppResult.Error(ex.message, exception = ex))
        }
    }


    private suspend fun getMoviesByCollection(collection: MovieCollection): AppResult<List<MovieCardItem>> {

        return when (collection) {

            MovieCollection.Trending -> getMoviesTrending()

            MovieCollection.Recommended -> getMoviesRecommended()

            MovieCollection.Played -> getMoviesPlayed()

            MovieCollection.Collected -> getMoviesCollected()

            MovieCollection.Popular -> getMoviesPopular()

            MovieCollection.Anticipated -> getMoviesAnticipated()
        }
    }

    @TestOnly
    private suspend fun getMoviesByCollectionTest(collection: MovieCollection): AppResult<List<MovieCardItem>> {

        return when (collection) {

            MovieCollection.Recommended -> getMoviesRecommended()

            MovieCollection.Popular -> getMoviesPopular()

            else -> AppResult.Empty
        }
    }

    private suspend fun getMoviesTrending(): AppResult<List<MovieCardItem>> {

        val result = repository.trending()

        if (result is AppResult.Success) {

            val listTKMovies = result.data.map { x -> x.movie }

            val movieCardItems = createListCardItemsByTKMovies(
                tkMovies = listTKMovies,
                collection = MovieCollection.Trending
            ).mapIndexed { i, item ->

                val trending = result.data[i]
                // TODO: use res-string or translate
                item.chips.add("${trending.watchers.toFormat()} personas viendo")

                return@mapIndexed item
            }

            return AppResult.Success(movieCardItems)
        }

        return AppResult.Error(message = "Error loading @Trending movies")
    }

    private suspend fun getMoviesRecommended(): AppResult<List<MovieCardItem>> {

        val result = repository.recommended()

        if (result is AppResult.Success) {

            val listTKMovies = result.data.map { x -> x.movie }

            val movieCardItems = createListCardItemsByTKMovies(
                tkMovies = listTKMovies,
                collection = MovieCollection.Recommended
            ).mapIndexed { i, item ->

                val recommended = result.data[i]
                // TODO: use res-string or translate
                item.chips.add("${recommended.userCount.toFormat()} personas recomiendan")

                return@mapIndexed item
            }

            return AppResult.Success(movieCardItems)
        }

        return AppResult.Error(message = "Error loading @Recommended movies")
    }

    private suspend fun getMoviesPlayed(): AppResult<List<MovieCardItem>> {

        val result = repository.played()

        if (result is AppResult.Success) {

            val listTKMovies = result.data.map { x -> x.movie }

            val movieCardItems = createListCardItemsByTKMovies(
                tkMovies = listTKMovies,
                collection = MovieCollection.Played
            ).mapIndexed { i, item ->

                val played = result.data[i]
                // TODO: use res-string or translate
                item.chips.add("${played.watcherCount.toFormat()} visitas")
                item.chips.add("${played.playCount.toFormat()} reproducciones")

                return@mapIndexed item
            }

            return AppResult.Success(movieCardItems)
        }

        return AppResult.Error(message = "Error loading @Played movies")
    }

    private suspend fun getMoviesCollected(): AppResult<List<MovieCardItem>> {

        val result = repository.collected()

        if (result is AppResult.Success) {

            val listTKMovies = result.data.map { x -> x.movie }

            val movieCardItems = createListCardItemsByTKMovies(
                tkMovies = listTKMovies,
                collection = MovieCollection.Collected
            ).mapIndexed { i, item ->

                val collected = result.data[i]
                // TODO: use res-string or translate
                item.chips.add("${collected.collectedCount.toFormat()} colecciones")

                return@mapIndexed item
            }

            return AppResult.Success(movieCardItems)
        }

        return AppResult.Error(message = "Error loading @Collected movies")
    }

    private suspend fun getMoviesPopular(): AppResult<List<MovieCardItem>> {

        val result = repository.popular()

        if (result is AppResult.Success) {

            val listTKMovies = result.data

            val movieCardItems = createListCardItemsByTKMovies(
                tkMovies = listTKMovies,
                collection = MovieCollection.Popular
            )

            return AppResult.Success(movieCardItems)
        }

        return AppResult.Error(message = "Error loading @Popular movies")
    }

    private suspend fun getMoviesAnticipated(): AppResult<List<MovieCardItem>> {

        val result = repository.anticipated()

        if (result is AppResult.Success) {

            val listTKMovies = result.data.map { x -> x.movie }

            val movieCardItems = createListCardItemsByTKMovies(
                tkMovies = listTKMovies,
                collection = MovieCollection.Anticipated
            ).mapIndexed { i, item ->

                val anticipated = result.data[i]
                // TODO: use res-string or translate
                item.chips.add("${anticipated.listCount.toFormat()} listas")

                item.detail?.releaseDate?.let { date ->
                    val formatDate =
                        date.toFormat(DateTimeExtensions.Pattern.DateThree).capitalize()
                    item.chips.add(formatDate)
                }

                return@mapIndexed item
            }

            return AppResult.Success(movieCardItems)
        }

        return AppResult.Error(message = "Error loading @Anticipated movies")
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

                // Get movie-detail & set into card-item =>
                detail = getTMMovieDetail(it.ids.tmdb)
            }

            movieCardItems.add(cardItem)
        }

        return movieCardItems
    }


    private suspend fun getTMMovieDetail(movieId: Long): TMMovieDetail? {

        return when (val result = repository.tmMovieDetail(movieId)) {

            is AppResult.Success -> result.data

            else -> null
        }
    }
}