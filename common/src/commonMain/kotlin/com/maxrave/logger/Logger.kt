package com.maxrave.logger

import site.deltastudy.serein.core.logging.Logger as SereinLogger

/**
 * Legacy JVM/Kotlin facade for callers of com.maxrave.logger.Logger.
 *
 * Preserves the original binary class name and public method signatures while
 * delegating every operation to the Serein logger's single rolling-file writer.
 * Do not duplicate log state here.
 */
object Logger {
    const val APP_LOG = "app"
    const val ERROR_LOG = "error"

    val logDirectory: String?
        get() = SereinLogger.logDirectory

    fun enableFileLogging(directory: String) = SereinLogger.enableFileLogging(directory)

    fun logFiles(name: String): List<String> = SereinLogger.logFiles(name)

    fun d(tag: String, message: String) = SereinLogger.d(tag, message)

    fun i(tag: String, message: String) = SereinLogger.i(tag, message)

    fun w(tag: String, message: String) = SereinLogger.w(tag, message)

    fun e(tag: String, message: String, e: Throwable? = null) = SereinLogger.e(tag, message, e)
}

/** Keep the original runtime enum identity to preserve persisted compatibility. */
enum class LogLevel {
    DEBUG,
    INFO,
    WARN,
    ERROR,
}
