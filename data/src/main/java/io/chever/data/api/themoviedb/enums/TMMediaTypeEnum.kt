package io.chever.data.api.themoviedb.enums

import com.squareup.moshi.Json

/**
 * Represent valid media types values. Common use in Trending.
 */
enum class TMMediaTypeEnum(
    val value: String
) {

    /**
     *  Include all movies, TV shows and people in the results as a global trending list.
     */
    @Json(name = "all")
    All(value = "all"),

    /**
     * Show the trending movies in the results.
     */
    @Json(name = "movie")
    Movie(value = "movie"),

    /**
     * Show the trending TV shows in the results.
     */
    @Json(name = "tv")
    Tv(value = "tv"),

    /**
     * Show the trending people in the results.
     */
    @Json(name = "person")
    Person(value = "person")
}