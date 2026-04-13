package com.dexdiary.utils

import android.content.Context
import android.media.MediaPlayer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FocusAudioPlayer @Inject constructor() {
    private var mediaPlayer: MediaPlayer? = null

    enum class Soundscape {
        NONE, RAIN, LOFI, FOREST, CAFE
    }

    fun play(context: Context, soundscape: Soundscape) {
        stop()
        if (soundscape == Soundscape.NONE) return

        // In a real app, these would be raw resources
        // mediaPlayer = MediaPlayer.create(context, getResource(soundscape)).apply {
        //     isLooping = true
        //     start()
        // }
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
