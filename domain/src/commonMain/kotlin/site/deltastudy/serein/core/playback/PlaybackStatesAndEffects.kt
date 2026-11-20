package site.deltastudy.serein.core.playback

/**
 * Serein-facing playback states, audio routing and effect types.
 *
 * These are source aliases only, with the same original runtime types, enum
 * names, media queue values, serialization and DataStore compatibility.
 * Do not introduce replacement data classes or change the persisted format.
 */
typealias AudioEffects = com.maxrave.domain.data.player.AudioEffects
typealias DelayEffect = com.maxrave.domain.data.player.DelayEffect
typealias DelayTaps = com.maxrave.domain.data.player.DelayTaps
typealias ReverbEffect = com.maxrave.domain.data.player.ReverbEffect
typealias ReverbPreset = com.maxrave.domain.data.player.ReverbPreset
typealias EightDEffect = com.maxrave.domain.data.player.EightDEffect
typealias EightDDirection = com.maxrave.domain.data.player.EightDDirection
typealias EightDOrbit = com.maxrave.domain.data.player.EightDOrbit
typealias AudioOutput = com.maxrave.domain.data.player.AudioOutput
typealias AudioOutputKind = com.maxrave.domain.data.player.AudioOutputKind
typealias GenericCastState = com.maxrave.domain.data.player.GenericCastState

typealias QueueData = com.maxrave.domain.mediaservice.handler.QueueData
typealias RepeatState = com.maxrave.domain.mediaservice.handler.RepeatState
typealias PlayerEvent = com.maxrave.domain.mediaservice.handler.PlayerEvent
typealias SimpleMediaState = com.maxrave.domain.mediaservice.handler.SimpleMediaState
typealias ControlState = com.maxrave.domain.mediaservice.handler.ControlState
typealias NowPlayingTrackState = com.maxrave.domain.mediaservice.handler.NowPlayingTrackState
typealias SleepTimerState = com.maxrave.domain.mediaservice.handler.SleepTimerState
typealias AddToQueueBlock = com.maxrave.domain.mediaservice.handler.AddToQueueBlock
typealias PlaylistType = com.maxrave.domain.mediaservice.handler.PlaylistType
typealias ToastType = com.maxrave.domain.mediaservice.handler.ToastType
