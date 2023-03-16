package io.chever.shared.extension

import io.chever.shared.observability.AppLogger
import java.text.Normalizer
import java.util.Locale

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
 * Represent an empty ("") string by default.
 */
fun String.Companion.empty() = ""

/**
 * Represent an error ("N/A") string by default.
 */
fun String.Companion.error() = "N/A"

/**
 * Return an empty string if this is null.
 */
fun String?.emptyIfNull() = this ?: ""


/**
 * TODO: document function
 */
fun String.capitalize(): String = try {

    this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault())
        else it.toString()
    }

} catch (ex: Exception) {

    AppLogger.warning(ex, ex.message)
    this
}

/**
 * TODO: document function
 */
fun String.capitalizeWords(): String = try {

    this.split(" ").joinToString(" ") { word ->

        word.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault())
            else it.toString()
        }
    }

} catch (ex: Exception) {

    AppLogger.warning(ex, ex.message)
    this
}

/**
 * TODO: document function
 */
fun String.deleteShortWords(
    maxLength: Int = 2
): String = this.split(" ")
    .filter { w -> w.length > maxLength }
    .joinToString(" ")

/**
 * TODO: document function
 */
fun String.normalize(
    form: Normalizer.Form = Normalizer.Form.NFD
): String = try {

    Normalizer
        .normalize(this, form)
        .replace("[^\\p{ASCII}]".toRegex(), "")
        .trim()
        .lowercase()

} catch (ex: Exception) {

    AppLogger.warning(ex, ex.message)
    this
}

/**
 * TODO: document function
 */
fun String.toSlug(
    deleteShortWords: Boolean = false
): String {
    var slug = this

    if (deleteShortWords)
        slug = deleteShortWords()

    return slug.normalize()
        .replace(' ', '-')
}