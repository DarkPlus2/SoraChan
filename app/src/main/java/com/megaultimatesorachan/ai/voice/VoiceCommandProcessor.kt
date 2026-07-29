package com.megaultimatesorachan.ai.voice

import com.megaultimatesorachan.ai.commands.CommandRouter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VoiceCommandProcessor @Inject constructor(
    private val commandRouter: CommandRouter,
    private val languageDetector: LanguageDetector
) {
    suspend fun process(spokenText: String): String {
        val lang = languageDetector.detectLanguage(spokenText)
        return commandRouter.route(spokenText, lang)
    }
}
