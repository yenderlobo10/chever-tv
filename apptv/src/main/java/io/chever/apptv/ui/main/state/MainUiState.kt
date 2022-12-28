package io.chever.apptv.ui.main.state

import io.chever.apptv.model.CollectionMediaItem

data class MainUiState(

    val state: MainState = MainState.Loading,
    val homeCollections: List<CollectionMediaItem> = emptyList()
) {

    val isLoading = state is MainState.Loading

    val isSuccess = state is MainState.Success

    val isError = state is MainState.Error
}
