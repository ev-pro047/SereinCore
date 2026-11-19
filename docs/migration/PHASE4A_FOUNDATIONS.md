# Phase 4A — Serein foundation namespace migration

Status: **isolated candidate — NOT merged, requires SereinPro/CircleCI compilation and device validation.**

## Objective and scope
Migrate the smallest source-stable foundation first. This is not the bulk package rename of all modules.

1. The canonical logger implementation now lives at `site.deltastudy.serein.core.logging.Logger`.
2. `com.maxrave.logger.Logger` remains a **Kotlin source alias** to that same object. Existing import sites and file-logging behavior are preserved; no second logger or second writer is created.
3. The `com.maxrave.logger.LogLevel` enum intentionally retains its original runtime type and values. A new `site.deltastudy.serein.core.logging.LogLevel` alias points at it to avoid changing serialized enum identity.
4. The two foundation Android **library** namespaces become:
   - `common`: `site.deltastudy.serein.core.common`
   - `ktorExt`: `site.deltastudy.serein.core.network`
   Their **Kotlin** package names outside the logging implementation intentionally remain unchanged until downstream importers and expect/actual bindings can migrate safely.
5. Instrumentation test expectations follow the new common library namespace.
6. A multiplatform commonTest checks the old and new logger names resolve to the same object and enum values.

## Explicitly unchanged
- SereinPro Android `applicationId = "site.deltastudy.serein"`, debug install behavior, signatures and app user-data path.
- Gradle module names and project dependency paths.
- `common` public `com.maxrave.common` Kotlin interfaces/constants used by SereinPro and services.
- `ktorExt` KMP `expect/actual` packages for engine, HMAC and Brotli (renaming just one source set would break iOS/JVM).
- Data, domain, and media subtrees (unchanged exact trees from Phase 3):
  - data: `abc0c6a77a6a265d9b66a3e4d2c6f3ae754852e0`
  - domain: `270e7e93d1755ff6f9dc3f9449b9674bc6618df9`
  - media: `0601b604363d63542f3997b61e09ab85e67a43bf`
- Room schema/version, database filenames, DataStore keys, downloaded files, login tokens, settings and playlists.
- `AppIdentity` user-agent behavior and legacy integration strings. Changes there require independent service-level verification.

## Compatibility boundary
The old `Logger` declaration is now a **Kotlin typealias**, which preserves Kotlin source imports but **does not create a JVM class at the old fully qualified name**. Do not use this in independently compiled Java consumers or reflection without a separate compatibility analysis. The application compiles core modules from source; the CircleCI APK build is the mandatory gate.

## Validation order
1. Verify exact staged tree and unchanged storage modules.
2. Build `:common:jvmTest` to exercise the namespace compatibility test.
3. Build `:androidApp:assembleDebug` through the SereinPro pipeline after updating its pinned core gitlink and tree guards.
4. Review app compilation diagnostics for Java/reflection callers of the former logger FQN. Stop if found.
5. Install the candidate APK as an upgrade and verify launch, playback, offline music, YouTube login, lyrics, charts, background audio, settings and saved data.
6. Merge Core and Pro only after evidence; do not enable automatic merge.

## Later Phase 4 steps
Migrate the `common` Kotlin symbols with safe wrappers, then `ktorExt` expect/actual declarations for all targets, then update downstream imports. Avoid global `com.maxrave` replacements, especially in Room/serialization and Android app identifiers.

Rollback to SereinCore Phase 3 pin `eecb1ac49b946e26743a6e3990c83a80f8055cf1` and SereinPro Phase 3 production baseline `298646aa14d842f2e3789d0f886001e89cc7ee74`.
