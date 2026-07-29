package com.megaultimatesorachan.ai.ai

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiRepository @Inject constructor(
    private val client: GeminiClient,
    private val personalityManager: PersonalityManager
) {
    suspend fun sendMessage(
        message: String,
        history: List<ChatMessage> = emptyList()
    ): Result<String> {
        val result = client.chat(message, history)
        result.onSuccess { response ->
            val mood = personalityManager.detectMoodFromResponse(response)
            personalityManager.setMood(mood)
        }
        return result
    }
}
