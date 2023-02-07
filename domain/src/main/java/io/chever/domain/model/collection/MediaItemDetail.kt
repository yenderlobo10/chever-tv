package io.chever.domain.model.collection

import io.chever.domain.model.movie.MovieDetail
import io.chever.domain.model.show.ShowDetail

sealed class MediaItemDetail {

    data class Movie(
        val detail: MovieDetail
    ) : MediaItemDetail()

    data class Show(
        val detail: ShowDetail
    ) : MediaItemDetail()

    object Person : MediaItemDetail()

    object None : MediaItemDetail()
}
