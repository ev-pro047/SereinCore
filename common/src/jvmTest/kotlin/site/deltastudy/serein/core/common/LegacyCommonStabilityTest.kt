package site.deltastudy.serein.core.common

import com.maxrave.common.AppIdentity as LegacyAppIdentity
import com.maxrave.common.SUPPORTED_LOCATION as LegacyLocations
import com.maxrave.logger.LogLevel as LegacyLogLevel
import site.deltastudy.serein.core.logging.LogLevel as SereinLogLevel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertSame

/**
 * Phase 4D: protect already-installed app data and old JVM-facing API names.
 * No production implementation is migrated or deleted by these tests.
 */
class LegacyCommonStabilityTest {
    @Test
    fun appIdentityJvmClassAndDataClassMethodsRemain() {
        val original = Class.forName("com.maxrave.common.AppIdentity")
        assertSame(original, AppIdentity::class.java)
        assertSame(original, LegacyAppIdentity::class.java)
        val boolType = Boolean::class.javaPrimitiveType!!
        assertNotNull(original.getConstructor(
            String::class.java, String::class.java, String::class.java, boolType,
        ))
        assertNotNull(original.getMethod("getApplicationId"))
        assertNotNull(original.getMethod("getVersionName"))
        assertNotNull(original.getMethod("getPlatform"))
        assertNotNull(original.getMethod("getUserAgent"))
        assertNotNull(original.getMethod(
            "copy", String::class.java, String::class.java, String::class.java, boolType,
        ))
        val app = AppIdentity("site.deltastudy.serein", "phase4d", "Android")
        assertEquals("SimpMusic/phase4d (site.deltastudy.serein; Android)", app.userAgent)
    }

    @Test
    fun legacyLocationJvmObjectAndExactCountryOrderRemain() {
        val original = Class.forName("com.maxrave.common.SUPPORTED_LOCATION")
        val singleton = original.getDeclaredField("INSTANCE").get(null)
        val arrayFromLegacyJvm = original.getMethod("getItems").invoke(singleton)
        assertSame(SupportedLocations.items, LegacyLocations.items)
        assertSame(SupportedLocations.items, arrayFromLegacyJvm)
        val actual = LegacyLocations.items.map { it.toString() }
        val baseline = "AE,AR,AT,AU,AZ,BA,BD,BE,BG,BH,BO,BR,BY,CA,CH,CL,CO,CR,CY,CZ,DE,DK,DO,DZ,EC,EE,EG,ES,FI,FR,GB,GE,GH,GR,GT,HK,HN,HR,HU,ID,IE,IL,IN,IQ,IS,IT,JM,JO,JP,KE,KH,KR,KW,KZ,LA,LB,LI,LK,LT,LU,LV,LY,MA,ME,MK,MT,MX,MY,NG,NI,NL,NO,NP,NZ,OM,PA,PE,PG,PH,PK,PL,PR,PT,PY,QA,RO,RS,RU,SA,SE,SG,SI,SK,SN,SV,TH,TN,TR,TW,TZ,UA,UG,US,UY,VE,VN,YE,ZA,ZW".split(",")
        assertEquals(109, actual.size)
        assertEquals(baseline, actual)
    }

    @Test
    fun legacyLogLevelJvmEnumAndValueOrderRemain() {
        val original = Class.forName("com.maxrave.logger.LogLevel")
        assertSame(original, LegacyLogLevel::class.java)
        assertSame(original, SereinLogLevel::class.java)
        assertEquals(
            listOf("DEBUG", "INFO", "WARN", "ERROR"),
            LegacyLogLevel.entries.map { it.name },
        )
    }
}
