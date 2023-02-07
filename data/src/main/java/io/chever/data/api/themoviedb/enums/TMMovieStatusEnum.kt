package io.chever.data.api.themoviedb.enums

import com.squareup.moshi.Json

enum class TMMovieStatusEnum(val value: String) {

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

    Unknown("None")
}