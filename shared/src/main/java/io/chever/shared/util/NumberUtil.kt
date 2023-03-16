@file:Suppress("unused")

package io.chever.shared.util

import kotlin.random.Random

object NumberUtil {

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