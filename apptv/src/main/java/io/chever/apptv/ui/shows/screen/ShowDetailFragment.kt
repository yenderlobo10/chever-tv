package io.chever.apptv.ui.shows.screen

import io.chever.apptv.ui.media.MediaDetailFragment
import io.chever.domain.model.media.MediaItem
import io.chever.domain.model.media.MediaItemDetail
import io.chever.shared.observability.AppLogger

class ShowDetailFragment(
    mediaItem: MediaItem
) : MediaDetailFragment<MediaItemDetail.Show>(mediaItem) {

    override val mediaDetail: MediaItemDetail.Show
        get() = try {
            mediaItem.detail as MediaItemDetail.Show
        } catch (ex: ClassCastException) {
            AppLogger.error(ex)
            throw IllegalArgumentException("Invalid @mediaItem show detail")
        }

    override val pathBackground = mediaDetail.detail.backdropPath
    override val pathPoster = mediaDetail.detail.posterPath

    companion object {

        const val TAG = "chever-show-detail-fragment"
    }
}