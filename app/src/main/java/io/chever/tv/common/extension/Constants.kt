package io.chever.tv.common.extension

object Constants {

    /** Global Tag to [com.orhanobut.logger.Logger] */
    const val loggerTag = "CHEVER_LOGGER"

    /** Name file of app global variables */
    const val appPropertiesFileName = "chever.properties"

    /**
     * Name file of app key variables.
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