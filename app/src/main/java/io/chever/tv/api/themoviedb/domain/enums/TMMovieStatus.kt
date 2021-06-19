package io.chever.tv.api.themoviedb.domain.enums

import com.squareup.moshi.Json

enum class TMMovieStatus(val value: String) {

    @Json(name = "Rumored")
    Rumored("Rumored"),

    @Json(name = "Planned")
    Planned("Planned"),

    @Json(name = "In Production")
    InProduction("In Production"),

    @Json(name = "Post Production")
    PostProduction("Post Production"),

    @Json(name = "Released")
    Released("Released"),

    @Json(name = "Canceled")
    Canceled("Canceled"),
}