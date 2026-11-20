# Phase 5 — Consolidated music engine/service migration candidate

**Status: draft release candidate, pending compilation and device validation.**

This consolidated PR contains the complete **compatibility-first namespace and integration migration** for Phase 5A–5D. It is intentionally **not** a rewrite of the working Media3 or MPV audio algorithms, nor a deletion of inherited compatibility packages. The old runtime identities are contractually preserved to protect installed Serein users.

## 5A — Playback contracts

- Serein-owned `site.deltastudy.serein.core.playback` imports for `MediaPlayerInterface`, `MediaPlayerListener`, `MediaPlayerHandler`, media item/metadata/playback parameters and player errors.
- All are Kotlin `typealias` references to the original runtime classes. No new queue DTO, serializer, dependency-injection key or class is introduced.
- Android Media3 and desktop MPV player adapters import those new contracts. The implementations retain all existing method bodies.

## 5B — Android Media3 engine and service integration

- New Android engine boundary `site.deltastudy.serein.core.playback.android`, exposing Serein-owned aliases for the ExoPlayer adapter, crossfade player, media-session service and download service.
- Media3 Koin module now constructs/casts `SereinCrossfadePlayer` but resolves the **identical original class**, keeping Koin registration/runtime keys stable.
- Android service intents use `SereinMediaSessionService::class.java`, resolving the original `com.maxrave.media3.service.SimpleMediaService` binary class.
- Keep the original `SimpleMediaService.MusicBinder` access: a Kotlin typealias **cannot address the nested binder**, and the runtime binder contract must stay unchanged.
- The Android manifest component identifiers, MediaSession token, Android Auto, notification, audio focus, ExoPlayer, download service, cache providers and queue behavior remain unchanged.

## 5C — Desktop MPV, audio effects and queue bridge

- New `site.deltastudy.serein.core.playback.desktop.SereinMpvPlayerAdapter` typealias; Koin construction and retrieval now reference it, retaining the same original `MpvPlayerAdapter` class/key.
- Both Android and desktop player adapters import Serein-owned source aliases for audio effects, audio outputs, 8D effect/orbit and shared cast state.
- MPV backend `MpvPlayer.kt` and Android `AudioOutputRouter.kt` import the same Serein aliases without changing any algorithm, audio processing, device enumeration or threading.
- Serein-owned queue/control aliases include `QueueData`, `RepeatState`, `PlayerEvent`, `SimpleMediaState`, `ControlState`, `NowPlayingTrackState`, `SleepTimerState` and enum types. The old persisted values and JVM class identities remain.

## 5D — Integration compatibility and stabilization

- Domain common/JVM tests verify the original playback contract, exact runtime type identity, queued media value/copy semantics and audio-effect/route/queue state aliases.
- CI requires all Phase 3–4D compatibility tests plus Phase 5 tests and source-integrity hashes.
- A separate `:media-jvm:compileKotlin` compilation check proves desktop alias wiring compiles; **this is not a desktop MPV native runtime test**.
- Android APK compile and in-place phone upgrade test are mandatory before merge.

### Frozen/unchanged components

- SereinPro Android application ID and package used for the installed app.
- Old Media3 service class names in `androidApp/src/main/AndroidManifest.xml` (`SimpleMediaService` and `MusicDownloadService`), foreground service types, notifications, Android Auto and any media session IDs.
- `domain` original runtime classes, Room/DataStore schemas, saved playlists/queue positions, cache keys and paths, download index and offline media files.
- All existing Media3/MPV playback algorithms, crossfade, eight-dimensional effect engines, audio output routing, wake locks, repeat/shuffle and streaming behavior.
- Core `common`, `data`, `service` Git subtrees, including existing cryptographic and network functionality.

### Acceptance matrix

1. Full Git review confirms only new alias/test/docs and exact import/DI spelling updates in Core. No modifications to algorithms or persisted formats.
2. CI source integrity/ABI guards, Python verifier fixtures and `:common:jvmTest :ktorExt:jvmTest :domain:jvmTest` succeed; every required named test executed and passed.
3. Desktop `:media-jvm:compileKotlin` succeeds from the same pinned Core commit.
4. Android debug APK builds and is staged.
5. The user installs the APK **over** the existing app and validates queue persistence, shuffle/repeat, crossfade and effects, background audio, MediaSession controls, service restart, network streaming, Spotify/YouTube, lyrics, offline tracks, download resume and saved settings/account.
6. Desktop MPV playback and notifications require a later **desktop runtime** test before claiming cross-platform runtime verification.
7. Merge Core first, then Pro **only after** successful CI and user validation. Until then keep both PRs draft and production unchanged.

The migration replaces *source-level ownership names*, not the implementation provenance or the JVM/Android ABI. If completely eliminating all legacy runtime classes is a future goal, that requires a **separate** migration with data and Android component-name compatibility handling.

Rollback baselines: SereinCore Phase 4D `4ece203be8c0b569099c6bc8748c6bd975d390d2`, SereinPro Phase 4D `312f4ffd55547608ef7a2619856f28b930857f95`.
