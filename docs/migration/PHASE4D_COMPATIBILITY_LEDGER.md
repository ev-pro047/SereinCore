# Phase 4D — compatibility ledger and stabilization gate

Status: **draft candidate**, not a production release.

Phase 4D is intentionally a *test/documentation/CI-only* stabilization phase. We audited the original Kotlin/JVM classes and the first-party wrappers delivered through 4A–4C. Removing legacy runtime APIs in the installed app is neither necessary for the namespace migration nor proven safe. No compatibility facade is deleted in this phase.

## Compatibility inventory (do not remove or relocate)

| Legacy API | Serein-facing API | Reason to retain |
| --- | --- | --- |
| `com.maxrave.common.AppIdentity` | `site.deltastudy.serein.core.common.AppIdentity` (typealias) | Original JVM class, constructor, data-class copy and user-agent behavior |
| `com.maxrave.common.SUPPORTED_LOCATION` | `SupportedLocations` | Original JVM singleton, *same* shared Array, original 109-item ordering |
| `com.maxrave.logger.Logger` | `site.deltastudy.serein.core.logging.Logger` | Original JVM class/methods and a single logger writer |
| `com.maxrave.logger.LogLevel` | Serein namespace typealias | Original enum runtime identity and value names |
| `com.maxrave.ktorext.getEngine` | Serein `network.getEngine` | Original Android/JVM/iOS engines and expect/actual linkage |
| `com.maxrave.ktorext.crypto.Hmac` | Serein crypto typealias | Original HMAC JVM class, outputs and service signatures |
| `com.maxrave.ktorext.crypto.HmacUri` | Serein HmacUri | Unchanged API route strings |
| `com.maxrave.ktorext.encoding.brotli` and `createBrotliEncoder` | Serein network encoding wrappers | Existing Brotli behavior and codec instance |
| `com.maxrave.ktorext.curl.CurlLogger`, `CurlLoggerConfig` | Serein wrappers | Same Ktor plugin and privacy-preserving defaults |

## Frozen upgrade contracts

- Android application ID: `site.deltastudy.serein`.
- No changes to `data`, `domain`, `media`, Room schema, DataStore keys, database filenames, download folders, playback services or authentication.
- Keep original `AppIdentity.userAgent` behavior in this phase even where it still contains inherited branding; changing network identifiers requires a separate compatibility review.
- Keep original HMAC timestamp acceptance logic; behavior changes require security evaluation and cross-client compatibility testing.
- Keep original Kotlin Multiplatform `expect/actual` implementations and all existing iOS TODO limitations.
- Preserve all 19 original Ktor Kotlin files and source signatures.

## New automated regression checks

1. JVM binary tests prove legacy `AppIdentity`, `SUPPORTED_LOCATION` and `LogLevel` runtime identities and interfaces, and match the complete regional catalog against the reviewed 4B snapshot.
2. JVM Ktor tests prove deterministic HMAC output against JCA, validation of authentic/tampered requests, curl debug logging disabled and sensitive headers redacted by default, and Brotli codec identity/name.
3. Existing 4A–4C tests still run, with CircleCI-required XML evidence for every named compatibility test.
4. SereinPro CI verifies unchanged Android and Compose application Git trees, plus exact pinned SereinCore tree and protected package blobs.

## Release gate

- [ ] Source/ABI guard and Python XML-fixture tests pass on pinned SereinPro candidate.
- [ ] Both `:common:jvmTest` and `:ktorExt:jvmTest` execute **16 required cases** (ten from earlier phases, six new Phase 4D cases).
- [ ] APK builds and stage artifact succeeds on the same commit.
- [ ] Existing app upgrades without uninstalling and preserves logins, offline files, downloads, saved playlists/settings, region selection, lyrics, music playback and network services.
- [ ] Only then merge Core and Pro draft PRs, Core first.

Baseline: SereinCore `7d0f4a9e2488cd3211cf1835632225f20cd3061c` and SereinPro `d3e6dc01b447d11466d742c8a05d5eaef5b20ddd`.

Phase 5 must not remove these compatibility APIs until a separate full reverse-dependency and upgrade-safety audit approves it.
