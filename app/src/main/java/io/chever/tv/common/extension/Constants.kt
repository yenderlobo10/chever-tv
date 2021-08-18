package io.chever.tv.common.extension

object Constants {

    /** Global Tag to [com.orhanobut.logger.Logger] */
    const val loggerTag = "CHEVER_LOGGER"

    /** Name file of app global variables */
    // TODO: migrate key-values to here.
    const val appPropertiesFileName = "chever.properties"

    /**
     * YouTube base url to watch video.
     *
     * Eg. Use to extract url to reproduce movie trailer.
     */
    const val youtubeVideoBaseUrl = "https://youtube.com/watch?v="

    /**
     * Name file content app auth key values.
     *
     * **NOTE :**
     * You must create this file at assets, because is ignore from repository.
     *
     * **Variables :**
     * ~~~
     * + traktTVApiKey
     * + theMovieDBApiKey
     * ~~~
     */
    const val appKeysPropertiesFileName = "chever-keys.properties"
}