package site.deltastudy.serein.core.network

import com.maxrave.ktorext.curl.CurlLoggerConfig as LegacyCurlLoggerConfig
import com.maxrave.ktorext.encoding.createBrotliEncoder as legacyBrotliEncoder
import site.deltastudy.serein.core.network.crypto.Hmac
import site.deltastudy.serein.core.network.curl.CurlLoggerConfig
import site.deltastudy.serein.core.network.encoding.createBrotliEncoder
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertSame
import kotlin.test.assertTrue

/** Phase 4D: JVM network behavior checks with no production-code changes. */
class NetworkRuntimeStabilityTest {
    @Test
    fun hmacSha256OutputAndValidationStayCompatible() {
        val key = "phase4d-compatibility-key"
        val message = "timestamp:/v1/translated"
        val jca = Mac.getInstance("HmacSHA256").apply {
            init(SecretKeySpec(key.toByteArray(), "HmacSHA256"))
        }
        val baseline = Base64.getEncoder().encodeToString(jca.doFinal(message.toByteArray()))
        val signer = Hmac("HmacSHA256", key)
        assertEquals(baseline, signer.generateHmac(message))
        assertTrue(signer.validateHmac(message, baseline))
        assertFalse(signer.validateHmac("tampered:" + message, baseline))
    }

    @Test
    fun curlLoggerKeepsPrivacySafeDefaults() {
        val canonical = CurlLoggerConfig()
        val original: LegacyCurlLoggerConfig = canonical
        assertSame(canonical, original)
        assertFalse(canonical.enabled)
        assertFalse(canonical.includeHeaders)
        assertFalse(canonical.includeQuery)
        assertFalse(canonical.includeBody)
        assertTrue(canonical.handleCompression)
        val protected = canonical.redactHeaders.map { it.lowercase() }.toSet()
        for (header in listOf("authorization", "cookie", "set-cookie", "proxy-authorization", "x-api-key", "x-goog-authuser")) {
            assertTrue(header in protected, "Missing private header from curl redaction: " + header)
        }
    }

    @Test
    fun brotliJvmEncoderKeepsIdentityAndEncodingName() {
        val legacy = legacyBrotliEncoder()
        val canonical = createBrotliEncoder()
        assertSame(legacy, canonical)
        assertEquals("br", canonical.name)
    }
}
