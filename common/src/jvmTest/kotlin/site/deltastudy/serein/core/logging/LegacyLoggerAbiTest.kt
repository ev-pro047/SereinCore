package site.deltastudy.serein.core.logging

import kotlin.test.Test
import kotlin.test.assertNotNull

/**
 * Prevent accidental deletion of com.maxrave.logger.Logger's Java binary API.
 * Kotlin typealiases alone do NOT generate the legacy JVM class.
 */
class LegacyLoggerAbiTest {
    @Test
    fun oldLoggerJvmClassAndPublicMethodsRemain() {
        val clazz = Class.forName("com.maxrave.logger.Logger")
        assertNotNull(clazz.getDeclaredField("INSTANCE"))
        assertNotNull(clazz.getDeclaredField("APP_LOG"))
        assertNotNull(clazz.getDeclaredField("ERROR_LOG"))
        assertNotNull(clazz.getMethod("getLogDirectory"))
        assertNotNull(clazz.getMethod("enableFileLogging", String::class.java))
        assertNotNull(clazz.getMethod("logFiles", String::class.java))
        for (method in listOf("d", "i", "w")) {
            assertNotNull(clazz.getMethod(method, String::class.java, String::class.java))
        }
        assertNotNull(clazz.getMethod("e", String::class.java, String::class.java, Throwable::class.java))
        assertNotNull(Class.forName("com.maxrave.logger.LogLevel").enumConstants)
    }
}
