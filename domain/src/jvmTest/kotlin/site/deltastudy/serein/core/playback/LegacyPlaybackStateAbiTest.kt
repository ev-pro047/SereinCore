package site.deltastudy.serein.core.playback

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/** Protect runtime names relied on by saved queue, effects and JVM consumers. */
class LegacyPlaybackStateAbiTest {
    @Test
    fun queuedStateAndAudioEffectJvmClassesRemain() {
        val queue = Class.forName("com.maxrave.domain.mediaservice.handler.QueueData")
        val effect = Class.forName("com.maxrave.domain.data.player.AudioEffects")
        val repeat = Class.forName("com.maxrave.domain.mediaservice.handler.RepeatState")
        val route = Class.forName("com.maxrave.domain.data.player.AudioOutputKind")
        assertEquals(queue, QueueData::class.java)
        assertEquals(effect, AudioEffects::class.java)
        assertEquals(repeat, RepeatState::class.java)
        assertEquals(route, AudioOutputKind::class.java)
        assertNotNull(effect.getMethod("getDelay"))
        assertNotNull(effect.getMethod("getReverb"))
        assertNotNull(queue.getDeclaredClasses())
    }
}
