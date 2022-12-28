@file:Suppress("unused")

package io.chever.shared.extension

import android.content.Context
import android.content.res.Resources
import io.chever.shared.observability.AppLogger
import java.util.Properties

/**
 * Load and return a file properties(variables key/value) from assets.
 *
 * @param fileName Name of properties-file to load.
 */
fun Context.loadProperties(
    fileName: String
): Properties {

    try {

        this.assets?.let { assetManager ->

            val props = Properties()

            props.load(assetManager.open(fileName))

            return props
        }

    } catch (ex: Exception) {

        AppLogger.error(ex, ex.message!!)
    }

    return Properties()
}

/**
 * Get a property from file of properties(variables key/value) at assets.
 *
 * @param fileName Name of properties-file to load.
 * @param key Key name of property to obtain value.
 *
 * @return String @{value} or empty string if error produced.
 */
fun Context.getPropertyFromFile(
    fileName: String,
    key: String
): String {

    return try {

        this.loadProperties(fileName)
            .getProperty(key, "")

    } catch (ex: Exception) {

        AppLogger.error(ex, ex.message!!)
        ""
    }
}

/**
 * TODO: document function
 */
fun Context.isResourceExist(
    id: Int
): Boolean {

    return try {

        return this.resources.getResourceName(id) != null

    } catch (ex: Resources.NotFoundException) {
        AppLogger.warning(ex)
        false
    }
}