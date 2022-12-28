package io.chever.shared.observability.timber

import timber.log.Timber

/**
 * Implement Timber logger to production flavor.
 *
 * @see Timber
 */
class AppTimberReleaseTree : Timber.Tree() {

    override fun log(
        priority: Int,
        tag: String?,
        message: String,
        t: Throwable?
    ) {
        // TODO implement crashlytics
    }
}