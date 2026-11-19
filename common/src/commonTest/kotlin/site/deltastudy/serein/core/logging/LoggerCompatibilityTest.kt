package site.deltastudy.serein.core.logging

import com.maxrave.logger.Logger as LegacyLogger
import com.maxrave.logger.LogLevel as LegacyLogLevel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

/** Legacy calls route to the canonical logger; enum identity never changes. */
class LoggerCompatibilityTest {
    @Test
    fun legacyFacadeDelegatesToCanonicalLoggerState() {
        assertEquals(Logger.APP_LOG, LegacyLogger.APP_LOG)
        assertEquals(Logger.ERROR_LOG, LegacyLogger.ERROR_LOG)
        assertEquals(Logger.logDirectory, LegacyLogger.logDirectory)
        assertEquals(Logger.logFiles(Logger.APP_LOG), LegacyLogger.logFiles(LegacyLogger.APP_LOG))
    }

    @Test
    fun logLevelKeepsOriginalRuntimeIdentityAndValues() {
        assertSame(LegacyLogLevel.DEBUG, LogLevel.DEBUG)
        assertEquals(listOf("DEBUG", "INFO", "WARN", "ERROR"), LogLevel.entries.map { it.name })
    }
}
