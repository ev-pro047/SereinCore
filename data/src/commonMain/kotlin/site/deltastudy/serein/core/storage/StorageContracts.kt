package site.deltastudy.serein.core.storage

import DatabaseDao as LegacyDatabaseDao

/**
 * Serein-owned Kotlin names for the original Room and settings contracts.
 *
 * These are typealiases, not new database, DAO, entity, serializer or settings
 * classes. The runtime KClass, generated Room implementation, Koin keys and
 * existing on-device database/settings files remain exactly the same.
 *
 * Do not relocate MusicDatabase or its original DAO implementations without
 * a separately validated Room migration and Android upgrade plan.
 */
typealias SereinMusicDatabase = com.maxrave.data.db.MusicDatabase
typealias SereinDatabaseDao = LegacyDatabaseDao
typealias SereinSyncDao = com.maxrave.data.db.SyncDao
typealias SereinSettingsStore = com.maxrave.domain.manager.DataStoreManager

/** Immutable compatibility identifiers: never change without file migration. */
object SereinStorageIds {
    const val DATABASE_FILE_NAME = com.maxrave.common.DB_NAME
    const val SETTINGS_FILE_STEM = com.maxrave.common.SETTINGS_FILENAME
    const val EXOPLAYER_DATABASE_FILE_NAME = com.maxrave.common.EXOPLAYER_DB_NAME

    /** Android DataStore path is filesDir/datastore/<this value>. */
    val settingsPreferencesFileName: String get() = SETTINGS_FILE_STEM + ".preferences_pb"

    /** Library-sync keys are *not* stored in settings and are excluded from backups. */
    const val LIBRARY_SYNC_PREFERENCES_FILE_NAME = "library_sync.preferences_pb"
}
