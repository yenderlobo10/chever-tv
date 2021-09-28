package io.chever.tv.common.extension

import com.orhanobut.logger.Logger
import com.squareup.moshi.Moshi
import io.chever.tv.common.extension.Extensions.fromJson
import java.util.*

/**
 * TODO: document object
 */
object StringExtensions {

    const val PATTER_HTTP_URL =
        "^https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{2,256}\\.[a-z]{2,4}\\b([-a-zA-Z0-9@:%_+.~#?&/=]*)\$"

    const val PATTERN_MAGNET_URL = "^magnet:\\?xt=urn:[a-z0-9]+:[a-z0-9]{32,40}&dn=.+&tr=.+\$"


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

            Logger.e(ex.message!!, ex)
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

            Logger.e(ex, ex.message!!)
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

            Logger.e(ex, ex.message!!)
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
}