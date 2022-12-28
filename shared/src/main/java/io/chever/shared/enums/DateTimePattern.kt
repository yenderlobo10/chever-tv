package io.chever.shared.enums

/**
 * Represent available date-time formats to use.
 * ```
 * * Add more if required.
 */
@Suppress("unused")
enum class DateTimePattern(
    val path: String
) {

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
     * dd MMM yyyy - Ej. 27 dic 2022
     */
    DateFour("dd MMM yyyy"),

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