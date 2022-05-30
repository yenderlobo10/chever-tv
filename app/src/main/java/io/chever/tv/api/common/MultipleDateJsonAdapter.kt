package io.chever.tv.api.common

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class MultipleDateJsonAdapter : JsonAdapter<Date>() {

    override fun fromJson(reader: JsonReader): Date? {

        try {

            return when (reader.peek()) {

                JsonReader.Token.STRING -> parseStringFormat(reader)

                JsonReader.Token.NUMBER -> parseNumberFormat(reader)

                else -> reader.nextNull()
            }

        } catch (ex: Exception) {

            Timber.e(ex)
            return null
        }
    }


    /**
     * Try convert date in number format to [Date].
     */
    private fun parseNumberFormat(reader: JsonReader) = Date(reader.nextLong())

    /**
     * Try convert date in string format to [Date].
     */
    private fun parseStringFormat(reader: JsonReader): Date? {

        val dateString = reader.nextString()

        val dLocale = Locale.getDefault()

        listOf(
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", dLocale),
            SimpleDateFormat("yyyy-MM-dd", dLocale),
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss 'UTC'", dLocale)
        ).forEach { formatter ->

            try {
                return formatter.parse(dateString)
            } catch (ex: Exception) {
                return@forEach
            }
        }

        return null
    }


    override fun toJson(writer: JsonWriter, value: Date?) {
        throw NotImplementedError()
    }
}