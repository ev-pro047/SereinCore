package site.deltastudy.serein.core.network.curl

import com.maxrave.ktorext.curl.CurlLogger as LegacyCurlLogger

/**
 * Same Ktor plugin instance and redaction/default settings as before.
 * Keeping the original plugin prevents duplicate loggers or credential leaks.
 */
val CurlLogger = LegacyCurlLogger

/** Exact original Ktor configuration class; no second type is created. */
typealias CurlLoggerConfig = com.maxrave.ktorext.curl.CurlLoggerConfig
