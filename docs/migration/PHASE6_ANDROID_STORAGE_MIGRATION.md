# Phase 6 — Android data/storage ownership migration

Status: **candidate only; must pass CI and in-place Android upgrade verification before merge.**

## Inspected production storage contracts

- Android app ID: `site.deltastudy.serein`; **unchanged**.
- Room database: `com.maxrave.data.db.MusicDatabase`, version **28**, database filename **`Music Database`**. The exported Room schema v28 has **26 tables** and identity hash `b5e6ecfb9eb529f08c2d3ad92ab21b5a`.
- Room schemas **1–28** remain exported under `data/schemas/com.maxrave.data.db.MusicDatabase/`. All existing auto/manual migrations stay registered and untouched.
- Preferences file: Android `filesDir/datastore/settings.preferences_pb`, key names controlled by existing `DataStoreManagerImpl`.
- Library synchronization: independent `filesDir/datastore/library_sync.preferences_pb`; this stores device pairing/security keys. **Do not copy it into settings or include in backup/export.**
- Media3 cache/download index: `playerCache`, `downloadCache`, `canvasCache`, `exoplayer_internal.db`; all retained.
- Persistent queues, local playlists, history, account and analytics records use the existing Room entities and DAO contracts. **Do not move, recreate or rename entity tables.**

## Changes in this candidate

1. Add Serein-owned Kotlin `typealias` import paths for the original `MusicDatabase`, `DatabaseDao`, `SyncDao`, and `DataStoreManager`. This creates **no new database classes** or storage.
2. Retain existing database factory, converters, database path, Room schema and migration methods, DAO queries, Koin runtime keys, preference keys and download file locations.
3. Update a narrow set of Koin/source imports to use the Serein aliases. These aliases compile to the same original runtime classes.
4. Add Kotlin common/JVM regression tests for storage aliases, original JVM class names and exact database/preferences filenames.
5. Add a CI Python integrity check verifying the exact v28 exported schema, original migration/database/storage implementation Git blobs, app source/manifest, and absence of storage reinitialization/destructive migration calls.

## What Phase 6 does *not* do

There is **no Room schema upgrade from v28 to v29**, no `fallbackToDestructiveMigration()`, no wholesale import/export of user secrets, no reset of Spotify/YouTube accounts, and no creation of an empty Serein-named database. Renaming persistent objects without tested on-device migration would be unsafe. The old implementation packages intentionally remain until a separate fully validated storage migration can be justified.

## Release gates

- [ ] Source/Room schema integrity check passes on the exact pinned Core gitlink; all 28 exported schema files are still present and unchanged.
- [ ] Previous 24 JVM regression tests pass, plus three Phase 6 storage tests in `:data:jvmTest`.
- [ ] Desktop Kotlin source compilation gate remains (not a desktop runtime claim).
- [ ] Android debug APK builds.
- [ ] In-place upgrade from Phase 5 on the user's real Android phone without uninstalling; explicitly verify saved playlists, likes, queue position, history, offline downloads, sound settings and existing YouTube/Spotify logins.
- [ ] Merge SereinCore first and SereinPro second **only after validation**.

**Rollback:** SereinCore `8b02c535bd0f7ce46073f2f953794beec9a279d1`, SereinPro `d6c2e704a9e0f4ac1522078653bf4bd2247a0183`.
