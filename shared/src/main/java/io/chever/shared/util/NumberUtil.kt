@file:Suppress("unused")

package io.chever.shared.util

import java.util.UUID
import kotlin.random.Random

object NumberUtil {

    /**
     * Generate random GUID.
     *
     * Example: eee3effd-8cbd-423d-be44-9c86624468c8
     */
    fun genRandomGuid(): String = UUID.randomUUID().toString()

    /**
     * Generate random [Long] number id.
     */
    fun genRandomLongId(
        from: Long = Long.MIN_VALUE,
        until: Long = Long.MAX_VALUE
    ): Long = Random.nextLong(from, until)

    /**
     * Generate random [Int] number id.
     */
    fun genRandomIntId(
        from: Int = Int.MIN_VALUE,
        until: Int = Int.MAX_VALUE
    ): Int = Random.nextInt(from, until)
}