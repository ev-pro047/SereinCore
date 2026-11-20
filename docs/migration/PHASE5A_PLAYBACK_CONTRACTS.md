# Phase 5A — playback contract boundary (initial Phase 5 slice)

**Draft candidate: CI and device validation pending.** Previous Phase 4D is merged; this is not the completed music-engine migration.

## Architecture audit

Two existing playback implementations share the `domain` player contract:

- Android: `media/media3` (Media3/ExoPlayer, session service, crossfade, queue, cache and downloads).
- Desktop: `media/media-jvm` (MPV adapter, playback/effects, downloading and notifications).
- Both depend on the original `com.maxrave.domain.mediaservice.player.MediaPlayerInterface` and listener/handler contracts, with `GenericMediaItem` and metadata as shared queue payloads.

**Phase 5A** introduces Serein-owned Kotlin import paths for these existing types, updates selected Android/desktop adapter and DI source imports, and adds binary/source compatibility tests. It deliberately does not change playback logic, persistence or class identities.

## Compatibility constraints

- Keep *all original* JVM classes and interfaces in `com.maxrave.domain...`; new `site.deltastudy.serein.core.playback` names are Kotlin **typealiases**, not new runtime types.
- Keep the Android app ID, module names, manifest service class names, Media3 player/session/notification identity, audio focus, Android Auto, playlist queue ordering, shuffle/repeat, crossfade and wake locks unchanged.
- Do not change Media3 `SimpleCache` path, download index/database, URI, media IDs, cache keys, files, Room or DataStore format.
- Do not modify the existing MPV native integration, codec/effects implementation, or dependencies.
- The Android/Compose and all Core runtime implementation files should remain bytewise identical **except for the selected playback import lines**.
- `common`, `data` and `service` source Git subtrees are unchanged.

## Validation

1. Review Git diff: only added alias/test/docs and import-only edits in selected adapters/DI modules.
2. Execute full previous 16 Phase 3–4D JVM compatibility cases unchanged.
3. Run `:domain:jvmTest`; require three new common-test methods plus original JVM ABI reflection test; fail on missing/failed/skipped cases.
4. Build Android APK from the reviewed exact Core gitlink without uninstalling the current app.
5. On device, test uninterrupted playback, pause/seek/next, repeat/shuffle, queue persistence, playlists, streaming, crossfade/effects, offline downloads, background/locked screen, MediaSession notifications, Spotify/YouTube search and lyrics.
6. Test desktop MPV separately in a later release gate; Android device testing does not prove desktop functionality.

## Next slices (not yet implemented)

- **5B:** Android Media3 engine and service registration APIs (ABI and component-name protected).
- **5C:** Desktop MPV, audio-effects and queue bridge.
- **5D:** Playback/download/cache integration regression suite and stabilization.

**No automated merging.** Keep phase 4D production as the rollback point until all gates pass.
