@file:Suppress("unused")

package io.chever.shared.extension

import io.chever.shared.enums.DateTimePattern
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import timber.log.Timber


/**
 * TODO: Document function
 */
fun Date.year(): Int = this.onlyYear().toInt()

/**
 * TODO: Document function
 */
fun Date.onlyYear(): String = this.toFormat(DateTimePattern.Year)

/**
 * TODO: Document function
 */
fun Date.onlyMonth(): String = this.toFormat(DateTimePattern.MonthTwo)

/**
 * TODO: Document function
 */
fun Date.onlyMonthShort(): String = this.toFormat(DateTimePattern.MonthShort)

/**
 * TODO: Document function
 */
fun Date.onlyMonthFull(): String = this.toFormat(DateTimePattern.MonthFull)


/**
 * TODO: Document function
 */
fun Date.toFormat(
    pattern: DateTimePattern,
    locale: Locale = Locale.getDefault()
): String = toFormat(
    pattern = pattern.path,
    locale = locale
)

/**
 * TODO: Document function
 */
fun Date.toFormat(
    pattern: String,
    locale: Locale = Locale.getDefault()
): String {

    return try {

        SimpleDateFormat(
            pattern, locale
        )
            .format(this)

    } catch (ex: Exception) {

        Timber.e(ex, ex.message!!)
        String.error()
    }
}