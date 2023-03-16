package io.chever.apptv.ui.home.model

import io.chever.apptv.ui.home.enums.HomeCollection
import io.chever.domain.model.media.MediaItem

data class MediaCardItem(

    val mediaItem: MediaItem,
    val collection: HomeCollection
)
