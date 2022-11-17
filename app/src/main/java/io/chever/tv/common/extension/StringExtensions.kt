package io.chever.tv.common.extension

import com.squareup.moshi.Moshi
import io.chever.tv.common.extension.Extensions.fromJson
import java.text.Normalizer
import java.util.Locale
import timber.log.Timber

/**
 * TODO: document object
 */
object StringExtensions {

    const val PATTER_HTTP_URL =
        "^https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{2,256}\\.[a-z]{2,4}\\b([-a-zA-Z0-9@:%_+.~#?&/=]*)\$"

    const val PATTERN_MAGNET_URL = "^magnet:\\?xt=urn:[a-z0-9]+:[a-z0-9]{32,40}&dn=.+&tr=.+\$"

    const val PATTERN_IMDB_ID = "^tt[\\d]*\$"


    /**
     * TODO: document function
     */
    fun String.isHttpUrl(): Boolean =
        Regex(PATTER_HTTP_URL).matches(this)

    /**
     * TODO: document function
     */
    fun String.isMagnetUrl(): Boolean =
        Regex(PATTERN_MAGNET_URL, RegexOption.IGNORE_CASE).matches(this)

    /**
     * TODO: document function
     */
    fun String.isIMDBID(): Boolean =
        Regex(PATTERN_IMDB_ID).matches(this)

    /**
     * Represent an empty ("") string by functionality.
     */
    fun String.Companion.empty() = ""

    /**
     * TODO: document function
     */
    fun <T> String.fromJson(type: Class<T>): T? {

        return try {

            Moshi.Builder().fromJson(this, type)

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
            null
        }
    }


    /**
     * TODO: document function
     */
    fun String.capitalize(): String {

        return try {

            this.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                else it.toString()
            }

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
            this
        }
    }

    /**
     * TODO: document function
     */
    fun String.capitalizeWords(): String {

        return try {

            this.split(" ").joinToString(" ") { word ->

                word.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                    else it.toString()
                }
            }

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
            this
        }
    }

    /**
     * TODO: document function
     */
    fun String.deleteShortWords(maxLength: Int = 2): String {

        return this.split(" ")
            .filter { w -> w.length > maxLength }
            .joinToString(" ")
    }

    /**
     * TODO: document function
     */
    fun String.normalize(
        form: Normalizer.Form = Normalizer.Form.NFD
    ): String {

        return try {

            Normalizer
                .normalize(this, form)
                .replace("[^\\p{ASCII}]".toRegex(), "")
                .trim()
                .lowercase()

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
            this
        }
    }
}