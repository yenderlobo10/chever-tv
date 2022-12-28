package io.chever.apptv.ui.main.state

import io.chever.apptv.ui.home.model.MediaCardItem

sealed class MainState {

    object Loading : MainState()

    data class Success(
        val homeCollections: List<MediaCardItem>
    ) : MainState()

    object Error : MainState()
}
