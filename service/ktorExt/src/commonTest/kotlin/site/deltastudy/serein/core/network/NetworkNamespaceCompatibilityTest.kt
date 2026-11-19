package site.deltastudy.serein.core.network

import com.maxrave.ktorext.getEngine as legacyGetEngine
import com.maxrave.ktorext.crypto.Hmac as LegacyHmac
import com.maxrave.ktorext.crypto.HmacUri as LegacyHmacUri
import com.maxrave.ktorext.curl.CurlLogger as LegacyCurlLogger
import io.ktor.util.ContentEncoder
import site.deltastudy.serein.core.network.crypto.Hmac
import site.deltastudy.serein.core.network.crypto.HmacUri
import site.deltastudy.serein.core.network.curl.CurlLogger
import site.deltastudy.serein.core.network.encoding.createBrotliEncoder
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertSame

/** Ensures Serein-owned packages share the *existing* network implementations. */
class NetworkNamespaceCompatibilityTest {
    @Test
    fun engineFactoryDelegatesToOriginalPlatformEngine() {
        assertSame(legacyGetEngine(), getEngine())
    }

    @Test
    fun curlLoggerRetainsOriginalPluginInstance() {
        assertSame(LegacyCurlLogger, CurlLogger)
    }

    @Test
    fun hmacAliasAndUriPathsRemainCompatible() {
        val newHmac: Hmac = LegacyHmac("HmacSHA256", "phase4c-compat-test")
        val oldHmac: LegacyHmac = newHmac
        assertSame(oldHmac, newHmac)
        assertEquals(LegacyHmacUri.BASE_HMAC_URI, HmacUri.BASE_HMAC_URI)
        assertEquals(LegacyHmacUri.TRANSLATED_HMAC_URI, HmacUri.TRANSLATED_HMAC_URI)
        assertEquals(LegacyHmacUri.VOTE_HMAC_URI, HmacUri.VOTE_HMAC_URI)
        assertEquals(LegacyHmacUri.VOTE_TRANSLATED_HMAC_URI, HmacUri.VOTE_TRANSLATED_HMAC_URI)
    }

    @Test
    fun brotliFactoryPreservesContentEncoderApi() {
        // Only inspect the common API here. The upstream iOS encoder is
        // not implemented yet; executing it on iOS would be an existing error.
        val factory: () -> ContentEncoder = ::createBrotliEncoder
        assertNotNull(factory)
    }
}
