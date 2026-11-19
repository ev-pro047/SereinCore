package site.deltastudy.serein.core.network.crypto

/**
 * Source-level Serein alias: the actual runtime type, constructor and HMAC
 * behavior remain com.maxrave.ktorext.crypto.Hmac on every platform.
 */
typealias Hmac = com.maxrave.ktorext.crypto.Hmac

/** New namespace, values unchanged from the original HmacUri object. */
object HmacUri {
    const val BASE_HMAC_URI = com.maxrave.ktorext.crypto.HmacUri.BASE_HMAC_URI
    const val TRANSLATED_HMAC_URI = com.maxrave.ktorext.crypto.HmacUri.TRANSLATED_HMAC_URI
    const val VOTE_HMAC_URI = com.maxrave.ktorext.crypto.HmacUri.VOTE_HMAC_URI
    const val VOTE_TRANSLATED_HMAC_URI = com.maxrave.ktorext.crypto.HmacUri.VOTE_TRANSLATED_HMAC_URI
}
