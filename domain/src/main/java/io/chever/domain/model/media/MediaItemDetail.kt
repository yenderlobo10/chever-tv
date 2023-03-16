package io.chever.domain.model.media

import io.chever.domain.model.movie.MovieDetail
import io.chever.domain.model.show.ShowDetail
import java.io.Serializable

sealed class MediaItemDetail : Serializable {

    data class Movie(
        val detail: MovieDetail
    ) : MediaItemDetail()

    data class Show(
        val detail: ShowDetail
    ) : MediaItemDetail()

    object Person : MediaItemDetail()

    object None : MediaItemDetail()
}
