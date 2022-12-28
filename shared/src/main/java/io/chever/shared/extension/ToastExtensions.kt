@file:Suppress("unused")

package io.chever.shared.extension

import android.content.Context
import android.widget.Toast

/**
 * Show toast during short time.
 *
 * @param message Text to show.
 */
fun Context.showShortToast(
    message: CharSequence
): Toast {

    return Toast.makeText(this, message, Toast.LENGTH_SHORT).also {
        it.show()
    }
}

/**
 * Show toast during short time.
 *
 * @param resMessage Id of string resource with text to show.
 */
fun Context.showShortToast(
    resMessage: Int
): Toast {

    return Toast.makeText(this, resMessage, Toast.LENGTH_SHORT).also {
        it.show()
    }
}

/**
 * Show toast during long time.
 *
 * @param message Text to show.
 */
fun Context.showLongToast(
    message: CharSequence
): Toast {

    return Toast.makeText(this, message, Toast.LENGTH_LONG).also {
        it.show()
    }
}

/**
 * Show toast during long time.
 *
 * @param resMessage Id of string resource with text to show.
 */
fun Context.showLongToast(
    resMessage: Int
): Toast {

    return Toast.makeText(this, resMessage, Toast.LENGTH_LONG).also {
        it.show()
    }
}