package io.chever.data.api.themoviedb.enums

import io.chever.data.api.themoviedb.TMDBApiConfig

/**
 * TODO: Document enum class
 */
enum class TMImageSizeEnum(val value: String) {

    Original("original"),

    W200("w200"),

    W300("w300"),

    W400("w400"),

    W500("w500");


    /**
     * Create a complete url of TheMovieDB API Images by path.
     *
     * @param path TMDb url image path.
     */
    fun createImageUrl(
        path: String?
    ): String = TMDBApiConfig.imagesUrl.plus(this.value).plus(path)
}