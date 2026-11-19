# Phase 4A — Serein foundation namespace migration

Status: **isolated candidate — NOT merged, requires SereinPro/CircleCI compilation and device validation.**

## Objective and scope
Migrate the smallest source-stable foundation first. This is not the bulk package rename of all modules.

1. The canonical logger implementation now lives at `site.deltastudy.serein.core.logging.Logger`.
2. `com.maxrave.logger.Logger` remains a **source- and JVM-compatible forwarding facade** to the canonical Serein logger. Existing Kotlin and Java import sites and file-logging behavior are preserved; only the new canonical logger owns the writer and log directory.
3. The `com.maxrave.logger.LogLevel` enum intentionally retains its original runtime type and values. A new `site.deltastudy.serein.core.logging.LogLevel` alias points at it to avoid changing serialized enum identity.
4. The two foundation Android **library** namespaces become:
   - `common`: `site.deltastudy.serein.core.common`
   - `ktorExt`: `site.deltastudy.serein.core.network`
   Their **Kotlin** package names outside the logging implementation intentionally remain unchanged until downstream importers and expect/actual bindings can migrate safely.
5. Instrumentation test expectations follow the new common library namespace.
6. A multiplatform commonTest checks forwarded logger state and unchanged enum values; a JVM test inspects the original class and public method signatures via reflection.

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
The old `Logger` declaration remains a real JVM class with the previous fully qualified name and method signatures, forwarding to the canonical Serein logger. The JVM ABI test detects accidental loss of this compatibility. The CircleCI full APK build remains the mandatory gate.

## Validation order
1. Verify exact staged tree and unchanged storage modules.
2. Build `:common:jvmTest` to exercise both namespace compatibility and JVM legacy-class ABI tests.
3. Build `:androidApp:assembleDebug` through the SereinPro pipeline after updating its pinned core gitlink and tree guards.
4. Review JVM ABI test and compilation diagnostics for Java/reflection callers of the legacy logger FQN; do not remove the facade without a separate compatibility release.
5. Install the candidate APK as an upgrade and verify launch, playback, offline music, YouTube login, lyrics, charts, background audio, settings and saved data.
6. Merge Core and Pro only after evidence; do not enable automatic merge.

## Later Phase 4 steps
Migrate the `common` Kotlin symbols with safe wrappers, then `ktorExt` expect/actual declarations for all targets, then update downstream imports. Avoid global `com.maxrave` replacements, especially in Room/serialization and Android app identifiers.

Rollback to SereinCore Phase 3 pin `eecb1ac49b946e26743a6e3990c83a80f8055cf1` and SereinPro Phase 3 production baseline `298646aa14d842f2e3789d0f886001e89cc7ee74`.
