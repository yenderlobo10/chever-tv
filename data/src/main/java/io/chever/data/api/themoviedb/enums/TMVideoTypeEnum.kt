package io.chever.data.api.themoviedb.enums

import com.squareup.moshi.Json

enum class TMVideoTypeEnum(val value: String) {

    @Json(name = "Trailer")
    Trailer("Trailer"),

    @Json(name = "Teaser")
    Teaser("Teaser"),

    @Json(name = "Clip")
    Clip("Clip"),

    @Json(name = "Featurette")
    Feature("Featurette"),

    @Json(name = "Behind the Scenes")
    BehindScenes("Behind the Scenes"),

    @Json(name = "Bloopers")
    Bloopers("Bloopers"),
}