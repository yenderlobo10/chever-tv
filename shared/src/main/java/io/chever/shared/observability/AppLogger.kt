package io.chever.shared.observability

import io.chever.shared.BuildConfig
import io.chever.shared.observability.timber.AppLoggerTimber
import io.chever.shared.observability.timber.AppTimberReleaseTree
import timber.log.Timber

/**
 * Represent a logger implement.
 */
object AppLogger : AppLoggerDefault, AppLoggerTimber {

    // Defaults

    override fun error(message: String?, vararg args: Any?) {
        Timber.e(message, args)
    }

    override fun error(t: Throwable?, message: String?, vararg args: Any?) {
        Timber.e(t, message, args)
    }

    override fun error(t: Throwable?) {
        Timber.e(t)
    }

    override fun warning(message: String?, vararg args: Any?) {
        Timber.w(message, args)
    }

    override fun warning(t: Throwable?, message: String?, vararg args: Any?) {
        Timber.w(t, message, args)
    }

    override fun warning(t: Throwable?) {
        Timber.w(t)
    }

    override fun info(message: String?, vararg args: Any?) {
        Timber.i(message, args)
    }

    override fun info(t: Throwable?, message: String?, vararg args: Any?) {
        Timber.i(t, message, args)
    }

    override fun info(t: Throwable?) {
        Timber.i(t)
    }

    // Timber

    override val isPlanted: Boolean
        get() = Timber.treeCount > 0

    override fun wtf(message: String?, vararg args: Any?) {
        Timber.wtf(message, args)
    }

    override fun wtf(t: Throwable?, message: String?, vararg args: Any?) {
        Timber.wtf(t, message, args)
    }

    override fun wtf(t: Throwable?) {
        Timber.wtf(t)
    }


    /**
     * Initialize [Timber] logger provider.
     * @see <a href="https://github.com/JakeWharton/timber">Timber documentation</a>
     */
    override fun setup() {

        if (isPlanted) return

        Timber.plant(
            if (BuildConfig.DEBUG) Timber.DebugTree()
            else AppTimberReleaseTree()
        )
    }
}