package io.chever.apptv.ui.movies.screen

import io.chever.apptv.ui.media.MediaDetailFragment
import io.chever.domain.model.media.MediaItem
import io.chever.domain.model.media.MediaItemDetail
import io.chever.shared.observability.AppLogger

class MovieDetailFragment(
    mediaItem: MediaItem
) : MediaDetailFragment<MediaItemDetail.Movie>(mediaItem) {

    override val mediaDetail: MediaItemDetail.Movie
        get() = try {
            mediaItem.detail as MediaItemDetail.Movie
        } catch (ex: ClassCastException) {
            AppLogger.error(ex)
            throw IllegalArgumentException("Invalid @mediaItem movie detail")
        }

    override val pathBackground = mediaDetail.detail.backdropPath
    override val pathPoster = mediaDetail.detail.posterPath

    companion object {

        const val TAG = "chever-movie-detail-fragment"
    }
}