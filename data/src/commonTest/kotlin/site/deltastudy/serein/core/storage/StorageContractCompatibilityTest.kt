package site.deltastudy.serein.core.storage

import DatabaseDao as LegacyDatabaseDao
import com.maxrave.data.db.MusicDatabase
import com.maxrave.data.db.SyncDao
import com.maxrave.domain.manager.DataStoreManager
import kotlin.test.Test
import kotlin.test.assertEquals

/** Phase 6: only Kotlin import names change, never Room/Preferences identity. */
class StorageContractCompatibilityTest {
    @Test
    fun roomDatabaseAndDaoAliasesUseExistingRuntimeTypes() {
        assertEquals(MusicDatabase::class, SereinMusicDatabase::class)
        assertEquals(LegacyDatabaseDao::class, SereinDatabaseDao::class)
        assertEquals(SyncDao::class, SereinSyncDao::class)
    }

    @Test
    fun settingsStoreKeepsOriginalInterfaceAndIdentifiers() {
        assertEquals(DataStoreManager::class, SereinSettingsStore::class)
        assertEquals("Music Database", SereinStorageIds.DATABASE_FILE_NAME)
        assertEquals("settings", SereinStorageIds.SETTINGS_FILE_STEM)
        assertEquals("settings.preferences_pb", SereinStorageIds.settingsPreferencesFileName)
        assertEquals("exoplayer_internal.db", SereinStorageIds.EXOPLAYER_DATABASE_FILE_NAME)
        assertEquals("library_sync.preferences_pb", SereinStorageIds.LIBRARY_SYNC_PREFERENCES_FILE_NAME)
    }
}
