package site.deltastudy.serein.core.common

import com.maxrave.common.AppIdentity as LegacyAppIdentity
import com.maxrave.common.SUPPORTED_LOCATION as LegacyLocations
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

/** Cross-platform regressions for Phase 4B's common API migration. */
class CommonApiCompatibilityTest {
    @Test
    fun supportedLocationsShareOriginalValuesAndArrayIdentity() {
        val canonical = SupportedLocations.items
        assertSame(canonical, LegacyLocations.items)
        assertEquals(listOf("AE", "AR", "AT"), canonical.take(3).map { it.toString() })
        assertTrue(canonical.any { it.toString() == "IN" })
        assertEquals("ZW", canonical.last().toString())
        assertEquals(canonical.size, canonical.map { it.toString() }.distinct().size)
    }

    @Test
    fun appIdentityAliasPreservesOriginalClassAndUserAgent() {
        val identity: AppIdentity = LegacyAppIdentity(
            applicationId = "site.deltastudy.serein",
            versionName = "phase4b-test",
            platform = "Android",
        )
        val legacy: LegacyAppIdentity = identity
        assertSame(identity, legacy)
        assertEquals(
            "SimpMusic/phase4b-test (site.deltastudy.serein; Android)",
            identity.userAgent,
        )
        assertEquals(identity.copy(isDevBuild = true), legacy.copy(isDevBuild = true))
    }
}
