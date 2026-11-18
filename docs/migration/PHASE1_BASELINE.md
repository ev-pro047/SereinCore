# SereinCore migration — Phase 1 baseline and recovery plan

**Recorded:** 2026-11-19 (Asia/Kolkata)  
**Scope:** Documentation and branch isolation only. No package changes, behavior changes, or new server.

## 1. Immutable commit identifiers (do not treat branch names as immutable)

| Item | Verified value |
| --- | --- |
| Repository | `ev-pro047/SereinCore` (private) |
| Original `main` / baseline commit | `7e3708cfacef86470941c6fa2d9790eac8c9ef5c` |
| Imported source tree SHA | `178a82d335f7075e72ea5b3b30db2d3653d8b974` |
| Upstream repository and branch | `maxrave-dev/core`, `multiplatform` |
| Upstream source commit | `8413a257cbe45d102a9f101f2760e35b549e4d9e` |
| Backup branch | `backup/pre-migration-20261119` |
| Working branch | `migration/sereincore-phase1` |

The imported tree is byte-for-byte equivalent to the recorded upstream source tree. The private import has a NEW commit SHA and does NOT contain upstream commit history. Its source tree matches; its Git commit SHA does not. The original `LICENSE` is retained.

The backup branch is intended to be treated as read-only. It is a normal Git branch, not a protected or immutable tag. The exact baseline SHA above is the authoritative recovery identifier.

## 2. Scope and module inventory

The baseline contains 752 tracked files, including 647 Kotlin files and 17 Gradle module build files. Root module directories are `common/`, `data/`, `domain/`, `media/`, `service/`.

- Foundation: `common`, `domain`, `data`, `ktorExt`.
- Services: `kotlinYtmusicScraper`, `spotify`, `lyricsService`, `aiService`, `autoEqService`, `kizzy`, `discord`, `listenTogether`, `loginSync`.
- Media: `media3`, `media3-ui`, `media-jvm`, `media-jvm-ui`.

**Important:** SereinCore currently lacks a root `settings.gradle.kts`, `gradlew`, and root version catalog. It builds as modules inside SereinPro, which owns the Gradle root configuration. Do not claim a standalone core build has passed.

## 3. SereinPro compatibility contract

- SereinPro baseline commit: `8fce48abd01c634e96747679ce22ddc8bf754905`.
- SereinPro baseline `.gitmodules` URL: `https://github.com/maxrave-dev/core`.
- SereinPro baseline `core` gitlink (mode `160000`) points to upstream `8413a257cbe45d102a9f101f2760e35b549e4d9e`.
- Because SereinCore was snapshot-imported without upstream commit history, **changing only `.gitmodules` is insufficient**. Phase 2 must also change the gitlink to a commit available in the private SereinCore repository.
- Preserve the Gradle module names and source-set layout until the baseline integration build passes.
- Keep the Android application ID `site.deltastudy.serein` unchanged; Kotlin package names are a separate concern.
- Do not rename the persisted Room database file, DataStore preferences file, serialized field names, or deep-link schemes without a deliberate compatibility strategy.

## 4. Known concerns, to address after Phase 2 baseline builds

1. Upstream `simpmusic-files` remote config is fetched by `Ytmusic.getTidalRemoteConfig()`; `CommonRepositoryImpl` caches its `promos` field. The app also has independent hardcoded promotional UI.
2. `CurlLoggerPlugin.kt` defaults to no header redaction. `Ytmusic` logs authentication-derived data and uses verbose network logging. Prioritize an independently tested security fix.
3. The core's release endpoints still target `maxrave-dev/SimpMusic` and F-Droid identifiers.
4. `data` has Room `MusicDatabase` version 28 and exported historical schemas under `data/schemas/com.maxrave.data.db.MusicDatabase/`. Treat namespace changes as a high-risk migration.
5. Desktop file paths and login-sync wire identifiers include SimpMusic strings; do not rewrite them blindly.

## 5. Rollback / recovery

**No rollback is necessary during Phase 1**, since the baseline main branches are unchanged. If later migration changes break the app:

1. Stop merging or changing production branches; preserve any logs without sharing tokens/cookies.
2. In `SereinPro`, restore the exact baseline commit `8fce48abd01c634e96747679ce22ddc8bf754905` (or check out backup branch `backup/pre-sereincore-migration-20261119`) on a recovery branch.
3. Confirm `.gitmodules` is still the upstream URL and `core` gitlink remains upstream `8413a257cbe45d102a9f101f2760e35b549e4d9e`.
4. In `SereinCore`, the independent baseline is `7e3708cfacef86470941c6fa2d9790eac8c9ef5c`. Use backup `backup/pre-migration-20261119` as a read-only reference.
5. Validate and deploy a known-good build through the chosen CI route. **Do not force-push `main`, wipe app data, uninstall the release app, or run destructive database migrations** as a shortcut.

## 6. Phase 1 completion criteria

- [x] Record exact core and app baseline commit SHAs.
- [x] Record source tree hash, upstream source commit, and submodule gitlink.
- [x] Create recovery and working branches in both repositories.
- [x] Inventory core modules and dependency constraints.
- [x] Document high-risk integration and persistent-data boundaries.
- [ ] Phase 2: authenticate private submodule checkout on isolated SereinPro branch.
- [ ] Phase 2: re-pin core gitlink and prove unmodified baseline APK build.
- [ ] Phase 7: perform Android device and upgrade smoke tests (cannot be verified by source inspection).

**Change policy:** First prove baseline integration in Phase 2. No namespace moves, endpoint changes, or database changes in Phase 1. Keep GPLv3 license and attribution.
