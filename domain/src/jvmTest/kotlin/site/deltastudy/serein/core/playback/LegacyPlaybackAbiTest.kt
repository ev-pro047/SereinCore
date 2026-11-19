package site.deltastudy.serein.core.playback

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/** Prevent ABI breaks to the original player and queued media JVM identities. */
class LegacyPlaybackAbiTest {
    @Test
    fun legacyPlaybackClassesAndJVMMethodsRemain() {
        val player = Class.forName("com.maxrave.domain.mediaservice.player.MediaPlayerInterface")
        val listener = Class.forName("com.maxrave.domain.mediaservice.player.MediaPlayerListener")
        val handler = Class.forName("com.maxrave.domain.mediaservice.handler.MediaPlayerHandler")
        assertEquals(player, MediaPlayerInterface::class.java)
        assertEquals(listener, MediaPlayerListener::class.java)
        assertEquals(handler, MediaPlayerHandler::class.java)
        assertNotNull(player.getMethod("play"))
        assertNotNull(player.getMethod("pause"))
        assertNotNull(player.getMethod("seekTo", Long::class.javaPrimitiveType))
        assertNotNull(listener.getMethod("onPlaybackStateChanged", Int::class.javaPrimitiveType))

        val item = Class.forName("com.maxrave.domain.data.player.GenericMediaItem")
        val metadata = Class.forName("com.maxrave.domain.data.player.GenericMediaMetadata")
        assertEquals(item, GenericMediaItem::class.java)
        assertEquals(metadata, GenericMediaMetadata::class.java)
        assertEquals(
            Class.forName("com.maxrave.domain.data.player.GenericPlaybackParameters"),
            GenericPlaybackParameters::class.java,
        )
        assertEquals(
            Class.forName("com.maxrave.domain.data.player.PlayerError"),
            PlayerError::class.java,
        )
        assertNotNull(item.getMethod("getMediaId"))
        assertNotNull(item.getMethod("getMetadata"))
        assertNotNull(item.getMethod("getCustomCacheKey"))
    }
}
