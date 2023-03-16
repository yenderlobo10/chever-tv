@file:Suppress("unused")

package io.chever.shared.util

import java.util.UUID

object AppUtil {

    /**
     * Generate random GUID.
     *
     * Example: eee3effd-8cbd-423d-be44-9c86624468c8
     */
    fun genRandomGuid(): String = UUID.randomUUID().toString()
}