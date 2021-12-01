package io.chever.tv.common.extension

import android.util.Log
import timber.log.Timber

/**
 * TODO: document class
 */
class TimberReleaseTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {

        if (priority == Log.ERROR) {
            TODO("Send crashlytics report")
        }
    }
}