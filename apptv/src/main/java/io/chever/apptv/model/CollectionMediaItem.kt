package io.chever.apptv.model

import io.chever.apptv.ui.home.enums.HomeCollection
import io.chever.domain.model.collection.MediaItem

data class CollectionMediaItem(

    val mediaItem: MediaItem,
    val collection:HomeCollection
)
