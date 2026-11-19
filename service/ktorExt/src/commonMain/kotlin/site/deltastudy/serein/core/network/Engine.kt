package site.deltastudy.serein.core.network

import com.maxrave.ktorext.getEngine as legacyGetEngine
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory

/**
 * First-party Kotlin entry point. Delegates to the original expect/actual
 * engine so Android, JVM and iOS select precisely the same engine as before.
 */
fun getEngine(): HttpClientEngineFactory<HttpClientEngineConfig> = legacyGetEngine()
