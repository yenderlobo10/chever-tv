package io.chever.data.api.themoviedb.model.shows

import com.squareup.moshi.Json

data class TMShowCreator(

    val id: Long?,
    val name: String?,

    @Json(name = "profile_path")
    val profilePath: String?
)
