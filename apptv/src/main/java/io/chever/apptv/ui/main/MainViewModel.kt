package io.chever.apptv.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.chever.apptv.model.ResourceState
import io.chever.apptv.ui.home.enums.HomeCollection
import io.chever.apptv.ui.home.model.MediaCardItem
import io.chever.domain.enums.ListCollection
import io.chever.domain.enums.TimeWindowEnum
import io.chever.domain.model.resource.AppResult
import io.chever.domain.model.resource.ListCollectionParams
import io.chever.domain.usecase.media.ListMediaCollectionUseCase
import io.chever.domain.usecase.media.ListMediaTrendingUseCase
import io.chever.shared.observability.AppLogger
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

@HiltViewModel
class MainViewModel @Inject constructor(
    private val listMediaTrendingUseCase: ListMediaTrendingUseCase,
    private val listMediaCollectionUseCase: ListMediaCollectionUseCase
) : ViewModel() {

    private val mainState =
        MutableStateFlow<ResourceState<List<MediaCardItem>>>(ResourceState.Empty)
    private val homeCollections = mutableListOf<MediaCardItem>()

    suspend fun loadHomeCollections() = flow {

        when (mainState.value) {

            is ResourceState.Success -> emit(mainState.value)

            else -> {
                emit(ResourceState.Loading)

                HomeCollection.values().forEach {
                    loadMediaItemsByCollection(it)
                }

                whenLoadHomeCollectionsFinished()
                emit(mainState.value)
            }
        }
    }

    private fun whenLoadHomeCollectionsFinished() {

        mainState.value = when {

            homeCollections.isNotEmpty() -> ResourceState.Success(
                data = homeCollections.toList()
            )

            else -> ResourceState.Error(
                message = "Collection is empty"
            )
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

            else -> loadCollectionOther(
                collection = collection
            )
        }
    }

    private suspend fun loadCollectionTrending(
        collection: HomeCollection,
        timeWindow: TimeWindowEnum
    ) {

        listMediaTrendingUseCase(
            params = ListMediaTrendingUseCase.Params(
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

    private suspend fun loadCollectionOther(
        collection: HomeCollection
    ) {

        listMediaCollectionUseCase(
            params = ListCollectionParams(
                collection = collection.mapToListCollection()
            )
        ) { result ->

            when (result) {

                is AppResult.Success -> homeCollections.addAll(result.value.map {
                    MediaCardItem(
                        mediaItem = it,
                        collection = collection
                    )
                })

                is AppResult.Failure ->
                    AppLogger.warning(result.value.toString())
            }
        }
    }

    private fun HomeCollection.mapToListCollection(): ListCollection = when (this) {
        HomeCollection.Watched -> ListCollection.Watched
        HomeCollection.Recommended -> ListCollection.Recommended
        HomeCollection.Collected -> ListCollection.Collected
        HomeCollection.Popular -> ListCollection.Popular
        HomeCollection.Anticipated -> ListCollection.Anticipated
        else -> throw ClassCastException()
    }

}