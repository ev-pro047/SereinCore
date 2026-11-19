# SereinCore Phase 3 — security and upstream independence

**Status:** Candidate implementation on `migration/sereincore-phase3-security`. Requires a SereinPro/CircleCI integration build and device regression testing before production merge.

## Scope

1. The YouTube Music HTTP client no longer installs raw Ktor `Logging` or `CurlLogger` plugins. It no longer writes authentication-derived `SAPISIDHASH` values into application logs.
2. The shared `CurlLogger` plugin defaults to disabled. Even when deliberately enabled for debugging, logging request headers, query parameters or request bodies requires separate explicit opt-ins. Common credential headers are redacted by default.
3. HTTPS-to-HTTP redirects are not allowed in the main YouTube HTTP or download clients.
4. Authenticated YouTube account menu and account-switcher responses are no longer dumped into debug logs.
5. The app no longer downloads remote config from `maxrave-dev/simpmusic-files`; the compatibility method returns an empty `RemoteConfig`. `CommonRepositoryImpl` clears previously cached remote promotional JSON and does not overwrite cached TIDAL credentials.
6. The `UpdateRepository` implementation does not fetch or advertise SimpMusic/F-Droid releases as Serein updates. Low-level update URLs use Serein identities, but auto-update remains intentionally unavailable because SereinPro is private and has no public release feed.

## Deliberate compatibility boundaries

- This is **not a blanket namespace change**: leave `com.maxrave.*` and `org.simpmusic.*` package paths untouched until later phases.
- TIDAL client ID/secret previously obtained and cached may remain usable. Fresh installs do not receive those credentials automatically; optional TIDAL metadata lookups may be unavailable. YouTube Music playback/search is not deliberately disabled.
- A third-party `chart.simpmusic.org` chart lookup and legacy sharing/deep-link compatibility references remain in source; they must be evaluated separately before claiming *full* upstream independence. Do not turn charts off without a replacement and a device test.
- `SereinPro` app-side launcher banners, review/blog/star prompts and lyrics promos must be disabled on the stacked integration branch, including when an older app version cached the JSON.
- The Room database filename, version, schema history, DataStore keys (other than clearing the old promotional JSON payload), application ID, Kotlin packages and playback interfaces remain unchanged.
- `UpdateRepositoryImpl` retains its constructor signature so Koin modules continue to compile.
- Switching to stricter HTTPS redirects can affect unusual non-HTTPS sources; check radio/download fallback on a device.

## Verification gates

- [x] Source-level changes limited to network logging, remote config, and update feed behavior.
- [ ] Kotlin and Gradle compile through SereinPro with this exact SereinCore commit pinned.
- [ ] Static regression scan passes on the app + core checkout.
- [ ] Device playback, YouTube login, chart, download, TIDAL optional lookup and lyrics regression check.
- [ ] Confirm Serein owner popups are absent after multiple launches on an existing install.

**Security:** No API tokens or credential secrets belong in this public repository. Do not enable full request logging to debug signed-in users.
