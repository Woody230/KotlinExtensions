package com.bselzer.ktx.logging

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

object Logger {
    /**
     * The default message.
     */
    private const val defaultMessage: String = ""

    /**
     * Creates a verbose log.
     *
     * @param throwable the exception
     * @param message the block for creating the message
     */
    fun v(throwable: Throwable? = null, message: () -> String): Unit = Napier.v(message = message, throwable = throwable)

    /**
     * Creates a verbose log.
     *
     * @param throwable the exception
     * @param message the message
     */
    fun v(throwable: Throwable? = null, message: String? = defaultMessage): Unit = Napier.v(message = message ?: defaultMessage, throwable = throwable)

    /**
     * Creates a verbose log.
     *
     * @param message the message
     */
    fun v(message: String? = defaultMessage): Unit = v(null, message)

    /**
     * Creates an information log.
     *
     * @param throwable the exception
     * @param message the block for creating the message
     */
    fun i(throwable: Throwable? = null, message: () -> String): Unit = Napier.i(message = message, throwable = throwable)

    /**
     * Creates an information log.
     *
     * @param throwable the exception
     * @param message the message
     */
    fun i(throwable: Throwable? = null, message: String? = defaultMessage): Unit = Napier.i(message = message ?: defaultMessage, throwable = throwable)

    /**
     * Creates an information log.
     *
     * @param message the message
     */
    fun i(message: String? = defaultMessage): Unit = i(null, message)

    /**
     * Creates a debug log.
     *
     * @param throwable the exception
     * @param message the block for creating the message
     */
    fun d(throwable: Throwable? = null, message: () -> String): Unit = Napier.d(message = message, throwable = throwable)

    /**
     * Creates a debug log.
     *
     * @param throwable the exception
     * @param message the message
     */
    fun d(throwable: Throwable? = null, message: String? = defaultMessage): Unit = Napier.d(message = message ?: defaultMessage, throwable = throwable)

    /**
     * Creates a debug log.
     *
     * @param message the message
     */
    fun d(message: String? = defaultMessage): Unit = d(null, message)

    /**
     * Creates a warning log.
     *
     * @param throwable the exception
     * @param message the block for creating the message
     */
    fun w(throwable: Throwable? = null, message: () -> String): Unit = Napier.w(message = message, throwable = throwable)

    /**
     * Creates a warning log.
     *
     * @param throwable the exception
     * @param message the message
     */
    fun w(throwable: Throwable? = null, message: String? = defaultMessage): Unit = Napier.w(message = message ?: defaultMessage, throwable = throwable)

    /**
     * Creates a warning log.
     *
     * @param message the message
     */
    fun w(message: String? = defaultMessage): Unit = w(null, message)

    /**
     * Creates an error log.
     *
     * @param throwable the exception
     * @param message the block for creating the message
     */
    fun e(throwable: Throwable? = null, message: () -> String): Unit = Napier.e(message = message, throwable = throwable)

    /**
     * Creates an error log.
     *
     * @param throwable the exception
     * @param message the message
     */
    fun e(throwable: Throwable? = null, message: String? = defaultMessage): Unit = Napier.e(message = message ?: defaultMessage, throwable = throwable)

    /**
     * Creates an error log.
     *
     * @param message the message
     */
    fun e(message: String? = defaultMessage): Unit = e(null, message)

    /**
     * Creates a "What a Terrible Failure" log for scenarios that should never happen.
     *
     * @param throwable the exception
     * @param message the block for creating the message
     */
    fun wtf(throwable: Throwable? = null, message: () -> String): Unit = Napier.wtf(message = message, throwable = throwable)

    /**
     * Creates a "What a Terrible Failure" log for scenarios that should never happen.
     *
     * @param throwable the exception
     * @param message the message
     */
    fun wtf(throwable: Throwable? = null, message: String? = defaultMessage): Unit = Napier.wtf(message = message ?: defaultMessage, throwable = throwable)

    /**
     * Creates a "What a Terrible Failure" log for scenarios that should never happen.
     *
     * @param message the message
     */
    fun wtf(message: String? = defaultMessage): Unit = wtf(null, message)

    /**
     * Creates a debug log indicating the execution time of the [block].
     *
     * @param message the message
     * @param block the block to execute
     * @return the result of the block
     */
    inline fun <R> time(message: String, crossinline block: () -> R): R = time({ message }, block)

    /**
     * Creates a debug log indicating the execution time of the [block].
     *
     * @param message the message
     * @param block the block to execute
     * @return the result of the block
     */
    @OptIn(ExperimentalTime::class)
    inline fun <R> time(noinline message: () -> String, crossinline block: () -> R): R {
        val (result, duration) = measureTimedValue(block)
        d { message() + " Executed for $duration." }
        return result
    }

    /**
     * Clears all logging capabilities.
     */
    fun clear(): Unit = Napier.takeLogarithm()

    /**
     * Enables logging capabilities for debug mode.
     */
    fun enableDebugging(): Unit = Napier.base(DebugAntilog())
}
