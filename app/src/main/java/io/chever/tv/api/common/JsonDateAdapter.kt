package io.chever.tv.api.common

import com.orhanobut.logger.Logger
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

/**
 * Custom json date adapter, to convert
 * json string date into [Date] object.
 */
class JsonDateAdapter {

    private val dateFormat = SimpleDateFormat("yyyy-MM-ddTHH:mm:ss.sssZ", Locale.US)


    @ToJson
    fun toJson(date: Date): String {

        return try {

            dateFormat.format(date)

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
            "null"
        }
    }

    @FromJson
    fun fromJson(date: String): Date? {

        return try {

            dateFormat.parse(date)

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
            null
        }
    }


    companion object {

        fun create() = JsonDateAdapter()
    }
}