package io.chever.apptv.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.chever.apptv.ui.home.enums.HomeCollection
import io.chever.apptv.ui.home.model.MediaCardItem
import io.chever.apptv.ui.main.state.MainState
import io.chever.domain.enums.TimeWindowEnum
import io.chever.domain.model.result.AppResult
import io.chever.domain.usecase.collection.ListTrendingUseCase
import io.chever.shared.observability.AppLogger
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class MainViewModel @Inject constructor(
    private val listTrending: ListTrendingUseCase
) : ViewModel() {

    private val _mainState = MutableStateFlow<MainState>(MainState.Loading)
    val mainState: StateFlow<MainState> = _mainState

    private val homeCollections = mutableListOf<MediaCardItem>()

    suspend fun loadHomeCollections() {

        HomeCollection.values().forEach {
            loadMediaItemsByCollection(it)
        }

        whenLoadHomeCollectionsFinished()
    }

    private fun whenLoadHomeCollectionsFinished() {

        _mainState.value = when {

            homeCollections.isNotEmpty() -> MainState.Success(
                homeCollections = homeCollections.toList()
            )

            else -> MainState.Error
        }

        homeCollections.clear()
    }

    private suspend fun loadMediaItemsByCollection(
        collection: HomeCollection
    ) {
        when (collection) {

            HomeCollection.TrendingDay -> loadCollectionTrending(
                collection = collection,
                timeWindow = TimeWindowEnum.Day
            )

            HomeCollection.TrendingWeek -> loadCollectionTrending(
                collection = collection,
                timeWindow = TimeWindowEnum.Week
            )

            else -> {
                // TODO
            }
        }
    }

    private suspend fun loadCollectionTrending(
        collection: HomeCollection,
        timeWindow: TimeWindowEnum
    ) {

        listTrending(
            params = ListTrendingUseCase.Params(
                timeWindow = timeWindow
            )
        ) { result ->

            when (result) {

                is AppResult.Success -> {
                    homeCollections.addAll(result.value.map {
                        MediaCardItem(
                            mediaItem = it,
                            collection = collection
                        )
                    })
                }

                is AppResult.Failure ->
                    AppLogger.warning(result.value.toString())
            }
        }
    }
}