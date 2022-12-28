package io.chever.shared.extension

import android.content.Context
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