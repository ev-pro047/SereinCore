package site.deltastudy.serein.core.playback.android

/**
 * Source-only Serein ownership boundary around existing Media3 engine classes.
 *
 * Kotlin typealiases preserve *exactly* the JVM classes used by the manifest,
 * Android MediaSession tokens, download manager, Media3 cache, Koin and callers.
 * In particular, never register an alias as a new Android component.
 */
typealias SereinExoPlayerAdapter = com.maxrave.media3.exoplayer.ExoPlayerAdapter
internal typealias SereinCrossfadePlayer = com.maxrave.media3.exoplayer.CrossfadeExoPlayerAdapter
internal typealias SereinMediaSessionService = com.maxrave.media3.service.SimpleMediaService
internal typealias SereinMusicDownloadService = com.maxrave.media3.service.download.MusicDownloadService
