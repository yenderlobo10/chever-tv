package io.chever.tv.common.extension

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.widget.Toast
import com.orhanobut.logger.Logger
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.chever.tv.common.extension.StringExtensions.empty
import io.chever.tv.common.extension.StringExtensions.fromJson
import java.io.InputStream
import java.util.*

/**
 * TODO: document object
 * TODO: refactor class
 */
@Suppress(
    "unused",
    "MemberVisibilityCanBePrivate"
)
object Extensions {

    //#region Toast extensions

    fun Context.showShortToast(message: CharSequence): Toast {

        return Toast.makeText(this, message, Toast.LENGTH_SHORT).also {
            it.show()
        }
    }

    fun Context.showShortToast(resMessage: Int): Toast {

        return Toast.makeText(this, resMessage, Toast.LENGTH_SHORT).also {
            it.show()
        }
    }

    fun Context.showLongToast(message: CharSequence): Toast {

        return Toast.makeText(this, message, Toast.LENGTH_LONG).also {
            it.show()
        }
    }

    fun Context.showLongToast(resMessage: Int): Toast {

        return Toast.makeText(this, resMessage, Toast.LENGTH_LONG).also {
            it.show()
        }
    }

    //#endregion


    //#region Context extensions

    /**
     * Load and return a file properties(variables key/value) from assets.
     *
     * @param fileName Name of properties-file to load.
     */
    fun Context.loadProperties(fileName: String): Properties {

        try {

            this.assets?.let { assetManager ->

                val props = Properties()

                props.load(assetManager.open(fileName))

                return props
            }

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
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
    fun Context.getPropertyFromFile(fileName: String, key: String): String {

        return try {

            this.loadProperties(fileName).getProperty(key, "")

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
            ""
        }
    }

    /**
     * TODO: document function
     */
    fun Context.isResourceExist(id: Int): Boolean {

        return try {

            return this.resources.getResourceName(id) != null

        } catch (ex: Resources.NotFoundException) {
            false
        }
    }

    /**
     * TODO: document function
     */
    fun Context.getResStringByName(name: String): String? {

        return try {

            val resId = this.resources.getIdentifier(
                name,
                "string",
                this.packageName
            )

            if (resId > 0)
                this.resources.getString(resId)
            else
                null

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
            null
        }
    }

    //#endregion


    //#region View extensions

    fun View.show() {

        try {

            this.visibility = View.VISIBLE

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
        }
    }

    fun View.gone() {

        try {

            this.visibility = View.GONE

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
        }
    }

    fun View.hide() {

        try {

            this.visibility = View.GONE

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
        }
    }

    //#endregion


    //#region Other extensions

    fun InputStream.likeString(): String {

        return try {

            String(this.readBytes())

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
            String.empty()
        }
    }

    fun <T> InputStream.fromJson(type: Class<T>): T? {

        return try {

            likeString().fromJson(type)

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
            null
        }
    }

    fun <T> Moshi.Builder.fromJson(json: String, type: Class<T>): T? {

        return try {

            this.add(KotlinJsonAdapterFactory()).build()?.let {

                it.adapter(type)?.fromJson(json)!!
            }

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
            null
        }
    }

    //#endregion
}