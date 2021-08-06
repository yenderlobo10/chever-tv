package io.chever.tv.common.extension

import com.orhanobut.logger.Logger
import com.squareup.moshi.Moshi
import io.chever.tv.common.extension.Extensions.fromJson
import java.util.*

/**
 * TODO: document object
 */
object StringExtensions {

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
}