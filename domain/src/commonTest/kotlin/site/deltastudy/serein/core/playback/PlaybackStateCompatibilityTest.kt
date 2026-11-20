package site.deltastudy.serein.core.playback

import com.maxrave.domain.data.player.AudioEffects as LegacyEffects
import com.maxrave.domain.data.player.AudioOutputKind as LegacyOutputKind
import com.maxrave.domain.data.player.EightDEffect as LegacyEightD
import com.maxrave.domain.mediaservice.handler.PlayerEvent as LegacyPlayerEvent
import com.maxrave.domain.mediaservice.handler.RepeatState as LegacyRepeatState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class PlaybackStateCompatibilityTest {
    @Test
    fun audioEffectsRetainLegacyPresetsAndValues() {
        assertSame(LegacyEffects.NONE, AudioEffects.NONE)
        val effects = AudioEffects(delay = null, reverb = null)
        val legacy: LegacyEffects = effects
        assertSame(effects, legacy)
        assertEquals(LegacyEffects.NONE, effects)
    }

    @Test
    fun eightDEffectAndAudioOutputsUseOriginalEnums() {
        val eightD = EightDEffect()
        val legacyEightD: LegacyEightD = eightD
        assertSame(eightD, legacyEightD)
        assertEquals(LegacyOutputKind.entries.map { it.name },
            AudioOutputKind.entries.map { it.name })
        assertSame(LegacyOutputKind.BLUETOOTH, AudioOutputKind.BLUETOOTH)
        assertEquals(com.maxrave.domain.data.player.EightDOrbit::class, EightDOrbit::class)
    }

    @Test
    fun queueRepeatAndControlEventsKeepExistingRuntimeTypes() {
        assertEquals(LegacyRepeatState::class, RepeatState::class)
        assertEquals(LegacyPlayerEvent::class, PlayerEvent::class)
        assertEquals(com.maxrave.domain.mediaservice.handler.QueueData::class, QueueData::class)
        assertEquals(com.maxrave.domain.mediaservice.handler.SimpleMediaState::class,
            SimpleMediaState::class)
        assertEquals(com.maxrave.domain.mediaservice.handler.ControlState::class,
            ControlState::class)
        assertEquals(com.maxrave.domain.mediaservice.handler.NowPlayingTrackState::class,
            NowPlayingTrackState::class)
    }
}
