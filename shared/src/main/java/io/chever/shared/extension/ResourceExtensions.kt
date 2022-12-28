@file:Suppress("unused")

package io.chever.shared.extension

import android.content.res.Resources
import androidx.annotation.RawRes
import io.chever.shared.observability.AppLogger

/**
 * Load a raw resource file en return its content like string.
 *
 * @param rawId Id of raw resource.
 */
fun Resources.readRawResourceLikeString(
    @RawRes rawId: Int
): String {

    return try {

        String(
            this.openRawResource(rawId)
                .readBytes()
        )

    } catch (ex: Exception) {

        AppLogger.error(ex)
        ""
    }
}