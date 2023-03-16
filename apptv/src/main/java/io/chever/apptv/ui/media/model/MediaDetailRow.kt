package io.chever.apptv.ui.media.model

import io.chever.apptv.ui.media.MediaDetailFragment
import io.chever.domain.model.media.MediaItem
import io.chever.domain.model.media.MediaItemDetail

internal interface MediaDetailRow<T : MediaItemDetail> {

    val mediaItem: MediaItem

    fun detailFragment(): MediaDetailFragment<T>
}