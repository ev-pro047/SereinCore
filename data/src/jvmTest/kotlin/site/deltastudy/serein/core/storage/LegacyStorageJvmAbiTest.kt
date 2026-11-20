package site.deltastudy.serein.core.storage

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/** Prevent renamed Room classes from creating a second database or ABI break. */
class LegacyStorageJvmAbiTest {
    @Test
    fun originalRoomDatabaseAndDaoJvmNamesRemain() {
        val db = Class.forName("com.maxrave.data.db.MusicDatabase")
        val dao = Class.forName("DatabaseDao")
        val syncDao = Class.forName("com.maxrave.data.db.SyncDao")
        val prefs = Class.forName("com.maxrave.domain.manager.DataStoreManager")

        assertEquals(db, SereinMusicDatabase::class.java)
        assertEquals(dao, SereinDatabaseDao::class.java)
        assertEquals(syncDao, SereinSyncDao::class.java)
        assertEquals(prefs, SereinSettingsStore::class.java)
        assertNotNull(db.getMethod("getDatabaseDao"))
        assertNotNull(db.getMethod("getSyncDao"))
        assertNotNull(prefs.getMethod("getAppVersion"))
        assertNotNull(prefs.getMethod("getCookie"))
        assertNotNull(prefs.getMethod("getSaveRecentSongAndQueue"))
    }
}
