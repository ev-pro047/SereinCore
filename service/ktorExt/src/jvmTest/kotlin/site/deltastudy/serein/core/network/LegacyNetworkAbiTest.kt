package site.deltastudy.serein.core.network

import kotlin.test.Test
import kotlin.test.assertNotNull

/** Detect JVM ABI regressions in the existing HMAC runtime class. */
class LegacyNetworkAbiTest {
    @Test
    fun originalHmacJvmClassAndMethodsRemain() {
        val clazz = Class.forName("com.maxrave.ktorext.crypto.Hmac")
        assertNotNull(clazz.getConstructor(String::class.java, String::class.java))
        assertNotNull(clazz.getMethod("generateHmac", String::class.java))
        assertNotNull(clazz.getMethod("getMacTimestampPair", String::class.java))
        assertNotNull(clazz.getMethod("validateHmac", String::class.java, String::class.java))
        assertNotNull(clazz.getMethod("isValidTimestamp", String::class.java))
    }
}
