package io.chever.data.api.themoviedb.model.shows

import com.squareup.moshi.Json

data class TMShowCertification(

    val descriptors: List<String>,

    @Json(name = "iso_3166_1")
    val iso: String = "",

    val rating: String = ""
)
