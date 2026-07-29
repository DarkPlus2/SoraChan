package com.megaultimatesorachan.ai.voice

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import com.megaultimatesorachan.ai.ai.SupportedLanguage
import com.megaultimatesorachan.ai.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TTSManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val languageDetector: LanguageDetector
) {
    private var tts: TextToSpeech? = null
    private var isReady = false
    private var onSpeakingStateChanged: ((Boolean) -> Unit)? = null

    fun initialize(onReady: (Boolean) -> Unit = {}) {
        if (tts != null) { onReady(isReady); return }
        tts = TextToSpeech(context) { status ->
            isReady = status == TextToSpeech.SUCCESS
            if (isReady) {
                tts?.setPitch(Constants.TTS_DEFAULT_PITCH)
                tts?.setSpeechRate(Constants.TTS_DEFAULT_RATE)
            }
            onReady(isReady)
        }
        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) { onSpeakingStateChanged?.invoke(true) }
            override fun onDone(utteranceId: String?) { onSpeakingStateChanged?.invoke(false) }
            @Deprecated("Deprecated") override fun onError(utteranceId: String?) { onSpeakingStateChanged?.invoke(false) }
        })
    }

    fun setSpeakingListener(listener: (Boolean) -> Unit) { onSpeakingStateChanged = listener }

    fun speak(text: String, language: SupportedLanguage? = null) {
        if (!isReady || tts == null) return
        val lang = language ?: languageDetector.detectLanguage(text)
        tts?.language = Locale.forLanguageTag(languageDetector.toLocaleTag(lang))
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, UUID.randomUUID().toString())
    }

    fun stop() { tts?.stop(); onSpeakingStateChanged?.invoke(false) }
    fun shutdown() { tts?.stop(); tts?.shutdown(); tts = null; isReady = false }
}
