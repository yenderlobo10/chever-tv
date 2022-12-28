package io.chever.shared.observability

/**
 * Represent a contract to implement a Logger.
 */
interface AppLoggerDefault {

    /** Initialize logger provider. */
    fun setup()

    /**
     * Log an error message with optional format args.
     *
     * @param message Log message.
     * @param args Log format args (optional).
     */
    fun error(message: String?, vararg args: Any?)

    /**
     * Log an error exception and a message with optional format args.
     */
    fun error(t: Throwable?, message: String?, vararg args: Any?)

    /** Log an error exception. */
    fun error(t: Throwable?)


    /** Log a warning message with optional format args. */
    fun warning(message: String?, vararg args: Any?)

    /** Log a warning exception and a message with optional format args. */
    fun warning(t: Throwable?, message: String?, vararg args: Any?)

    /** Log a warning exception. */
    fun warning(t: Throwable?)


    /** Log an info message with optional format args. */
    fun info(message: String?, vararg args: Any?)

    /** Log an info exception and a message with optional format args. */
    fun info(t: Throwable?, message: String?, vararg args: Any?)

    /** Log an info exception. */
    fun info(t: Throwable?)
}