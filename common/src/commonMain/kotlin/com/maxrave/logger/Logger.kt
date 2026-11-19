package com.maxrave.logger

/**
 * Phase 4 migration shim.
 *
 * Existing Kotlin imports resolve to the same Serein logger object and retain
 * their output directory and rolling-file behavior. Keep this declaration until
 * all app/core consumers are migrated and their Android/iOS/JVM builds pass.
 */
typealias Logger = site.deltastudy.serein.core.logging.Logger

/**
 * Keep this enum at its original fully-qualified name for compatibility with
 * any existing serialization or saved values.
 */
enum class LogLevel {
    DEBUG,
    INFO,
    WARN,
    ERROR,
}
