package com.dexdiary.utils

import android.content.Context
import android.media.MediaRecorder
import java.io.File
import java.io.IOException
import java.util.UUID

class MediaRecorderHelper(private val context: Context) {
    private var mediaRecorder: MediaRecorder? = null
    private var currentFilePath: String? = null

    fun startRecording(): String? {
        val fileName = "audio_${UUID.randomUUID()}.aac"
        val file = File(context.filesDir, "audio/$fileName")
        file.parentFile?.mkdirs()
        currentFilePath = file.absolutePath

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(currentFilePath)
            try {
                prepare()
                start()
            } catch (e: IOException) {
                return null
            }
        }
        return currentFilePath
    }

    fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }
}
