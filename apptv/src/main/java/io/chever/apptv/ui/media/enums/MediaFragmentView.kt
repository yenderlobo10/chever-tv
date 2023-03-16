package io.chever.apptv.ui.media.enums

import io.chever.apptv.ui.media.MediaDetailFragment
import io.chever.apptv.ui.movies.screen.MovieDetailFragment
import io.chever.apptv.ui.shows.screen.ShowDetailFragment
import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.model.media.MediaItem
import io.chever.domain.model.media.MediaItemDetail

internal sealed class MediaFragmentView<T : MediaItemDetail>(
    val fragment: MediaDetailFragment<T>,
    val tag: String
) {

    data class Movie(
        val mediaItem: MediaItem
    ) : MediaFragmentView<MediaItemDetail.Movie>(
        fragment = MovieDetailFragment(mediaItem),
        tag = MovieDetailFragment.TAG
    )

    data class Show(
        val mediaItem: MediaItem
    ) : MediaFragmentView<MediaItemDetail.Show>(
        fragment = ShowDetailFragment(mediaItem),
        tag = ShowDetailFragment.TAG
    )

    companion object {

        fun fromMediaItem(
            item: MediaItem
        ) = with(item) {
            when (type) {
                MediaTypeEnum.Movie -> Movie(this)
                MediaTypeEnum.Show -> Show(this)
                else -> throw IllegalArgumentException("Invalid @mediaItem type")
            }
        }
    }
}
