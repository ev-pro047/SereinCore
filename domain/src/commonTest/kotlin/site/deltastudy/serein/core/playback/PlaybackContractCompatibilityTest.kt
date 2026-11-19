package site.deltastudy.serein.core.playback

import com.maxrave.domain.data.player.GenericMediaItem as LegacyMediaItem
import com.maxrave.domain.data.player.GenericMediaMetadata as LegacyMediaMetadata
import com.maxrave.domain.data.player.GenericPlaybackParameters as LegacyPlaybackParameters
import com.maxrave.domain.data.player.PlayerError as LegacyPlayerError
import com.maxrave.domain.mediaservice.handler.MediaPlayerHandler as LegacyMediaPlayerHandler
import com.maxrave.domain.mediaservice.player.MediaPlayerInterface as LegacyMediaPlayer
import com.maxrave.domain.mediaservice.player.MediaPlayerListener as LegacyMediaListener
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

/** Phase 5A: old and new names must compile to the same existing contracts. */
class PlaybackContractCompatibilityTest {
    @Test
    fun playbackInterfacesAreOriginalTypes() {
        assertEquals(LegacyMediaPlayer::class, MediaPlayerInterface::class)
        assertEquals(LegacyMediaListener::class, MediaPlayerListener::class)
        assertEquals(LegacyMediaPlayerHandler::class, MediaPlayerHandler::class)
        val oldListener: LegacyMediaListener = object : MediaPlayerListener {}
        val sereinListener: MediaPlayerListener = oldListener
        assertSame(oldListener, sereinListener)
    }

    @Test
    fun queuedMediaItemRetainsDataAndCopySemantics() {
        val metadata = GenericMediaMetadata(title = "Phase5A", artist = "Serein")
        val legacyMetadata: LegacyMediaMetadata = metadata
        assertSame(legacyMetadata, metadata)
        val item = GenericMediaItem(
            mediaId = "same-id",
            uri = "https://example.invalid/audio",
            metadata = metadata,
            customCacheKey = "old-cache-key",
        )
        val oldItem: LegacyMediaItem = item
        assertSame(item, oldItem)
        assertEquals(item, oldItem)
        assertEquals(item.copy(customCacheKey = "next-cache-key"),
            oldItem.copy(customCacheKey = "next-cache-key"))
        assertSame(LegacyMediaItem.EMPTY, GenericMediaItem.EMPTY)
        assertSame(LegacyMediaMetadata.EMPTY, GenericMediaMetadata.EMPTY)
    }

    @Test
    fun playbackParameterAndErrorRuntimeTypesRemainUnchanged() {
        val speed = GenericPlaybackParameters(speed = 1.25f, pitch = 1.0f)
        val legacySpeed: LegacyPlaybackParameters = speed
        assertSame(speed, legacySpeed)
        assertSame(LegacyPlaybackParameters.DEFAULT, GenericPlaybackParameters.DEFAULT)
        val error = PlayerError(42, "same-error", "playback")
        val legacyError: LegacyPlayerError = error
        assertSame(error, legacyError)
    }
}
