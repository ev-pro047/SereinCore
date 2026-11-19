package site.deltastudy.serein.core.network.encoding

import com.maxrave.ktorext.encoding.brotli as legacyBrotli
import com.maxrave.ktorext.encoding.createBrotliEncoder as legacyCreateBrotliEncoder
import io.ktor.client.plugins.compression.ContentEncodingConfig
import io.ktor.util.ContentEncoder

/** Retains each platform's original encoder implementation and defaults. */
fun createBrotliEncoder(): ContentEncoder = legacyCreateBrotliEncoder()

/** No second encoder registration logic: delegate to the legacy extension. */
fun ContentEncodingConfig.brotli(quality: Float? = null) {
    this.legacyBrotli(quality)
}
