package io.chever.tv.common.extension

import com.orhanobut.logger.Logger
import java.text.SimpleDateFormat
import java.util.*


/**
 * TODO: document object
 */
object DateTimeExtensions {

    /**
     * TODO: Document function
     */
    fun Date.toFormat(pattern: Pattern): String {

        return toFormat(pattern, Locale.getDefault())
    }

    /**
     * TODO: Document function
     */
    fun Date.toFormat(pattern: Pattern, locale: Locale): String {

        return try {

            SimpleDateFormat(
                pattern.value, locale
            )
                .format(this)

        } catch (ex: Exception) {

            Logger.e(ex, ex.message!!)
            this.toString()
        }
    }

    /**
     * TODO: Document function
     */
    fun Date.onlyYear(): String = this.toFormat(Pattern.Year)

    /**
     * TODO: Document function
     */
    fun Date.onlyMonth(): String = this.toFormat(Pattern.MonthTwo)

    /**
     * TODO: Document function
     */
    fun Date.onlyMonthShort(): String = this.toFormat(Pattern.MonthShort)

    /**
     * TODO: Document function
     */
    fun Date.onlyMonthFull(): String = this.toFormat(Pattern.MonthFull)


    /**
     * Represent available date-time formats to use.
     * ```
     * * Add more if required.
     */
    enum class Pattern(val value: String) {

        /**
         * dd/MM/yyyy - Ej. 14/07/2021
         */
        DateOne("dd/MM/yyyy"),

        /**
         * MMMM dd, yyyy - Ej. july 14, 2021
         */
        DateTwo("MMMM dd, yyyy"),

        /**
         * MMM dd, yyyy - Ej. jul 20, 2021
         */
        DateThree("MMM dd, yyyy"),

        /**
         * Standard ISO 8601 only date format yyyy-MM-dd -
         * Ej. 2021-07-14
         */
        DateIso("yyyy-MM-dd"),

        /**
         * Standard ISO 8601 only date format yyyy-MM-dd'T'HH:mm:ss -
         * Ej. 2021-07-14T06:36:25
         */
        DateTimeIso("yyyy-MM-dd'T'HH:mm:ss"),

        /**
         * Obtain year only.
         */
        Year("yyyy"),

        /**
         * Obtain number month - Ej. 2021-07-14T06:36:25 => 7
         */
        Month("M"),

        /**
         * Obtain number month as two digits format -
         * Ej. 2021-07-14T06:36:25 => 07
         */
        MonthTwo("MM"),

        /**
         * Obtain short name of month -
         * Ej. 2021-07-14T06:36:25 => jul
         */
        MonthShort("MMM"),

        /**
         * Obtain full name of month -
         * Ej. 2021-07-14T06:36:25 => july
         */
        MonthFull("MMMM")
    }
}