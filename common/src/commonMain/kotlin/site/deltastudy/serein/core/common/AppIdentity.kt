package site.deltastudy.serein.core.common

/**
 * New Kotlin import path for the *original* AppIdentity runtime class.
 *
 * This is a typealias, not a second data class. DI, serialized class names,
 * binary method signatures, equals/hashCode and userAgent remain unchanged.
 * Keep the legacy class until all consumers are migrated and binary-safe.
 */
typealias AppIdentity = com.maxrave.common.AppIdentity
