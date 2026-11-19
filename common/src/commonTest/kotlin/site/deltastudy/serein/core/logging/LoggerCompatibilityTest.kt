package site.deltastudy.serein.core.logging

import com.maxrave.logger.Logger as LegacyLogger
import com.maxrave.logger.LogLevel as LegacyLogLevel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

/** Run on every platform: old imports must keep using the exact same logger. */
class LoggerCompatibilityTest {
    @Test
    fun legacyAliasResolvesToCanonicalLogger() {
        assertSame(Logger, LegacyLogger)
        assertEquals(Logger.APP_LOG, LegacyLogger.APP_LOG)
        assertEquals(Logger.ERROR_LOG, LegacyLogger.ERROR_LOG)
        assertEquals(Logger.logDirectory, LegacyLogger.logDirectory)
    }

    @Test
    fun logLevelKeepsOriginalRuntimeIdentityAndValues() {
        assertSame(LegacyLogLevel.DEBUG, LogLevel.DEBUG)
        assertEquals(listOf("DEBUG", "INFO", "WARN", "ERROR"), LogLevel.entries.map { it.name })
    }
}
