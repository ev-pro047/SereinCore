# Phase 4B — SereinCore common API migration, slice 1

**Status: draft candidate; CI/JVM/APK and upgrade validation are required before merging.**

## Implemented
- New canonical `site.deltastudy.serein.core.common.SupportedLocations.items`.
- Legacy `com.maxrave.common.SUPPORTED_LOCATION.items` remains a real JVM object + getter referencing **the same array instance**.
- New Serein Kotlin import `site.deltastudy.serein.core.common.AppIdentity` aliases the *existing* `com.maxrave.common.AppIdentity` data class. It does **not** create or rename any JVM/serialized type.
- The value list and order are copied exactly, with no localization or region behavior change.
- Added multiplatform tests for shared reference identity, catalog ordering, and app identity compatibility.

## Explicit exclusions
- The original AppIdentity JVM class, constructor and userAgent string are unchanged.
- No `Config.OFFICIAL_PACKAGE_NAMES`, `SUPPORTED_LANGUAGE`, `ITAG`, `QUALITY`, `LibraryChipType`, Room, settings, data, domain, media or service migration in this slice.
- Gradle namespace and dependencies remain untouched; no separate logger object or storage change.

## Verification
1. Check unchanged Git subtrees `data`, `domain`, `media` and `service`.
2. Confirm original country codes/order via source-level guard.
3. Run `:common:jvmTest` including both new common API tests and Phase 4A logger ABI tests.
4. Compile the SereinPro Android APK from a pin to this exact Core commit.
5. Upgrade an installed APK and test account data, offline media, playback, lyrics, charts and region settings.

**Never merge on source guards alone.** Phase 4A Core main baseline: `c57fd476b7095e876997db3a3d5e734fff47deeb`.
