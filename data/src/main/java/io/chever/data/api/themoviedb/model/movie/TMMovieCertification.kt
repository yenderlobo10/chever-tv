package io.chever.data.api.themoviedb.model.movie

import com.squareup.moshi.Json
import java.util.Date

data class TMMovieCertification(

    val certification: String = "",
    val descriptors: List<String>,

    @Json(name = "iso_3166_1")
    val iso: String = "",

    val primary: Boolean = false,

    @Json(name = "release_date")
    val releaseDate: Date?
)
