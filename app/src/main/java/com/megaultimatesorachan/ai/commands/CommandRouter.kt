package com.megaultimatesorachan.ai.commands

import android.content.Context
import com.megaultimatesorachan.ai.ai.GeminiRepository
import com.megaultimatesorachan.ai.ai.SupportedLanguage
import com.megaultimatesorachan.ai.util.LocalResponseEngine
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommandRouter @Inject constructor(
    @ApplicationContext private val context: Context,
    private val systemCommands: SystemCommands,
    private val funCommands: FunAnimeCommands,
    private val geminiRepository: GeminiRepository,
    private val localResponseEngine: LocalResponseEngine
) {
    suspend fun route(input: String, language: SupportedLanguage): String {
        val normalized = input.trim().lowercase()
        systemCommands.handle(normalized, context)?.let { return it }
        funCommands.handle(normalized, language)?.let { return it }
        localResponseEngine.handle(normalized, language)?.let { return it }
        val result = geminiRepository.sendMessage(input)
        return result.getOrElse { localResponseEngine.getOfflineFallback(language) }
    }
}
