package io.chever.tv.common.extension

import android.content.Context
import com.orhanobut.logger.Logger
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * TODO: document object
 */
object NumberExtensions {

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
    fun Int.toFormat(): String = toFormat(Locale.getDefault())

    /**
     * TODO: document function
     */
    fun Int.toFormat(locale: Locale): String {

        return try {

            NumberFormat.getNumberInstance(locale).format(this)

        } catch (ex: Exception) {

            Logger.e(ex, ex.message!!)
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

            Logger.e(ex, ex.message!!)
            "N/A"
        }
    }

    /**
     * TODO: document function
     */
    fun Float.toFormatDecimalPercent(): String {

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

            ex.printStackTrace()
            "0%"
        }
    }
}