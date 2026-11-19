# Phase 4C — Serein-owned network API migration

**State: candidate only. Do not merge without paired SereinPro CircleCI and upgrade verification.**

## Goal
Replace application-facing `com.maxrave.ktorext` imports incrementally while retaining exact existing Ktor engine choices, HMAC runtime identity, Brotli platform behavior and curl plugin security defaults.

## Implemented
- `site.deltastudy.serein.core.network.getEngine()` delegates to the original Kotlin Multiplatform `expect/actual` implementation.
- `site.deltastudy.serein.core.network.crypto.Hmac` is a Kotlin typealias to the *existing* platform HMAC type. `HmacUri` re-exports its four fixed paths without changes.
- `site.deltastudy.serein.core.network.encoding.createBrotliEncoder()` and `brotli()` delegate to legacy implementations, preserving current compression behavior.
- `site.deltastudy.serein.core.network.curl.CurlLogger` references the **same Ktor plugin instance**; its credential-redaction and disabled-by-default security behavior is unchanged. `CurlLoggerConfig` aliases the old configuration class.
- Imported those Serein API entrypoints into SpotifyClient, SimpMusicLyrics, SimpMusicLyricsClient, ListenTogetherClient, LoginSyncClient, PairedSyncClient, DiscordOAuth, and Kizzy ApiService. These changes affect imports only, not request logic.
- Added cross-platform Ktor compatibility tests for engine factory, curl plugin object, HMAC identity/path constants, and Brotli API; plus JVM reflection tests for the original HMAC binary class.

## Proven by code/tree audit
All **19 Kotlin files** in original `service/ktorExt/src/*/kotlin/com/maxrave/ktorext` are unchanged from the Phase 4B baseline. The `common`, `data`, `domain` and `media` Git trees are unchanged.

## Compatibility boundaries
- The old network package remains public and callable by all existing consumers.
- The Android app ID, database, DataStore, settings keys, login sessions, offline downloads, playback code, and Gradle module names are untouched.
- Kotlin expect/actual implementations still use their original namespaces and platform source sets; we deliberately do not move them in this batch.
- **Existing iOS limitation:** `Hmac.ios.kt` methods and `BrotliEncoder.ios.kt` contain TODO/not-implemented stubs. Phase 4C preserves those pre-existing limitations and does not claim functional iOS network authentication/compression.
- `Hmac` source alias does not create a new runtime class; original JVM class remains available.

## Acceptance gates
1. Pin this exact Core source Git tree and original legacy-file blobs in the paired SereinPro CI PR.
2. Run Phase 3/4A/4B source checks and new Phase 4C source regression checks.
3. Run `:common:jvmTest :ktorExt:jvmTest`; explicitly verify all prior five common tests and the new network common/JVM tests.
4. Build and package the Android debug APK; install **over** the existing tested app.
5. Device-test background playback, YouTube login, Spotify, LRCLIB lyrics, charts, audio quality, offline downloads, room/listen-together connectivity, and stored data.
6. Merge Core first, Pro second **only after** the tests pass.

Rollback: SereinCore Phase 4B main `43b013a167dfbe485897e7142d69dec024b5075b`; SereinPro Phase 4B main `e61f1c7c357dba92dedd1be858832e87ac90afd1`.
