package io.chever.tv.api.themoviedb.domain.enums

import android.content.Context
import io.chever.tv.common.extension.Constants
import io.chever.tv.common.extension.Extensions.getPropertyFromFile

/**
 * TODO: Document enum class
 */
enum class TMImageSize(val value: String) {

    Original("original"),

    W200("w200"),

    W300("w300"),

    W400("w400"),

    W500("w500");


    /**
     * Create a complete url of TheMovieDB API Images by path.
     *
     * @param context Application context.
     * @param urlPath TMDb url image path.
     */
    fun createImageUrl(context: Context, urlPath: String?): String {

        return context.getPropertyFromFile(
            Constants.appPropertiesFileName,
            "theMovieDBImagesUrl"
        )
            .plus(this.value)
            .plus(urlPath)
    }
}