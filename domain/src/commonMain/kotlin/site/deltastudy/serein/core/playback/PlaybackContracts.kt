package site.deltastudy.serein.core.playback

/**
 * Stable Serein-owned Kotlin import paths for the existing playback contract.
 *
 * A typealias does not create a second class or interface. Existing binary
 * names, dependency-injection keys, persisted media item layouts, queued
 * metadata, IPC and Media3/MPV adapter signatures remain unchanged.
 *
 * Keep original com.maxrave.domain classes until an ABI and upgrade audit
 * expressly approves their removal in a future migration.
 */
typealias MediaPlayerInterface = com.maxrave.domain.mediaservice.player.MediaPlayerInterface
typealias MediaPlayerListener = com.maxrave.domain.mediaservice.player.MediaPlayerListener
typealias MediaPlayerHandler = com.maxrave.domain.mediaservice.handler.MediaPlayerHandler

typealias GenericMediaItem = com.maxrave.domain.data.player.GenericMediaItem
typealias GenericMediaMetadata = com.maxrave.domain.data.player.GenericMediaMetadata
typealias GenericPlaybackParameters = com.maxrave.domain.data.player.GenericPlaybackParameters
typealias PlayerError = com.maxrave.domain.data.player.PlayerError
