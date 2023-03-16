package io.chever.shared.extension

import android.view.View

/**
 * TODO: document function
 */
fun View.show(): View {
    this.visibility = View.VISIBLE
    return this
}

/**
 * TODO: document function
 */
fun View.gone(): View {
    this.visibility = View.GONE
    return this
}

/**
 * TODO: document function
 */
fun View.hide(): View {
    this.visibility = View.GONE
    return this
}