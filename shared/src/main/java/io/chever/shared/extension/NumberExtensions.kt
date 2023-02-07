@file:Suppress("unused")

package io.chever.shared.extension

import android.content.Context
import io.chever.shared.observability.AppLogger
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * TODO: document function
 */
fun Int.dpFromPx(context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (this.toFloat() * density).roundToInt()
}

/**
 * TODO: document function
 */
fun Int.pxFromDp(context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (this.toFloat() / density).roundToInt()
}

/**
 * TODO: document function
 */
fun String.toNumberFormat(
    locale: Locale = Locale.getDefault()
): String {
    return try {

        NumberFormat.getNumberInstance(locale).format(this)

    } catch (ex: Exception) {

        AppLogger.warning(ex)
        this
    }
}

/**
 * TODO: document function
 */
fun Int.toFormat(
    locale: Locale = Locale.getDefault()
): String {

    return try {

        NumberFormat.getNumberInstance(locale).format(this)

    } catch (ex: Exception) {

        AppLogger.warning(ex)
        this.toString()
    }
}

/**
 * TODO: document function
 */
fun Float.toFormat(
    locale: Locale = Locale.getDefault(),
    decimals: Int = 2
): String {

    return try {

        NumberFormat.getNumberInstance(locale).apply {
            minimumFractionDigits = decimals
            maximumFractionDigits = decimals
        }.format(this)

    } catch (ex: Exception) {

        AppLogger.warning(ex)
        this.toString()
    }
}

/**
 * TODO: document function
 */
fun Long.toCompactFormat(): String {

    return try {

        val suffix = arrayOf("", "K", "M", "B", "P")

        val dValue = this.toDouble()
        val thousandValue = 1000.0
        val digitsGroup = (log10(dValue) / log10(thousandValue)).toInt()
        val sValue = dValue / thousandValue.pow(digitsGroup)

        val result = DecimalFormat("#,##0.#").format(sValue)

        "$result${suffix[digitsGroup]}"

    } catch (ex: Exception) {

        AppLogger.warning(ex)
        "N/A"
    }
}

/**
 * TODO: document function
 */
fun Float.toDecimalPercentFormat(): String {

    return try {

        when (this) {
            in (1f..9.99f) -> {

                val divResult = (this / 10 * 100).toInt()
                "$divResult%"

            }
            in (0.01f..0.99f) -> {

                val divResult = (this * 100).toInt()
                "$divResult%"

            }
            else -> {
                "${this.toInt()}%"
            }
        }

    } catch (ex: Exception) {

        AppLogger.warning(ex)
        "0%"
    }
}

/**
 * TODO: document function
 */
fun Float.toPercentFormat(
    locale: Locale = Locale.getDefault(),
    decimals: Int = 0
): String {

    return try {

        val formatter = NumberFormat.getPercentInstance(locale).apply {
            minimumFractionDigits = decimals
            maximumFractionDigits = decimals
        }

        if (this > 1 || this < -1)
            formatter.format(this.div(100))
        else
            formatter.format(this)

    } catch (ex: Exception) {

        AppLogger.warning(ex)
        "0%"
    }
}

/**
 * TODO: document function
 */
fun Iterable<Int>.calculateAverage(): Int = try {
    this.average().roundToInt()
} catch (ex: Exception) {
    AppLogger.warning(ex)
    0
}