package io.chever.shared.observability.timber

import timber.log.Timber

/**
 * Represent [Timber] logger specific methods to implement.
 * @see Timber
 */
interface AppLoggerTimber {

    /**
     * Return "true" when Timber tree-count array is not empty, this means than
     * an instance of Timber has already been planted.
     * In another case return "false".
     *
     * This property should be validate before planting an instance of a Timber tree.
     */
    val isPlanted: Boolean

    /** Log an assert message with optional format args. */
    fun wtf(message: String?, vararg args: Any?)

    /** Log an assert exception and a message with optional format args. */
    fun wtf(t: Throwable?, message: String?, vararg args: Any?)

    /** Log an assert exception. */
    fun wtf(t: Throwable?)
}