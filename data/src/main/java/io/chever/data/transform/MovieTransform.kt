package io.chever.data.transform

import io.chever.data.api.themoviedb.model.TMMovieDetail
import io.chever.domain.model.movie.MovieDetail


fun TMMovieDetail.mapToMovieDetail() = MovieDetail(
    id = this.id
)