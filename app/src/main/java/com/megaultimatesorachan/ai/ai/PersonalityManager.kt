package com.megaultimatesorachan.ai.ai

import javax.inject.Inject
import javax.inject.Singleton

enum class SoraMood {
    NORMAL, HAPPY, BLUSH, THINKING, SURPRISED, SAD, EXCITED, SLEEPY, LISTENING, SPEAKING
}

@Singleton
class PersonalityManager @Inject constructor() {

    private var currentMood: SoraMood = SoraMood.NORMAL

    fun getCurrentMood(): SoraMood = currentMood

    fun setMood(mood: SoraMood) {
        currentMood = mood
    }

    fun detectMoodFromResponse(text: String): SoraMood {
        val lower = text.lowercase()
        return when {
            lower.contains("hehe") || lower.contains("yatta") || lower.contains("😊") || lower.contains("✨") -> SoraMood.HAPPY
            lower.contains("sorry") || lower.contains("sad") || lower.contains("😢") -> SoraMood.SAD
            lower.contains("wow") || lower.contains("!") || lower.contains("surprised") -> SoraMood.SURPRISED
            lower.contains("think") || lower.contains("hmm") -> SoraMood.THINKING
            lower.contains("blush") || lower.contains("😳") -> SoraMood.BLUSH
            lower.contains("sleep") || lower.contains("tired") -> SoraMood.SLEEPY
            else -> SoraMood.NORMAL
        }
    }

    fun getGreeting(language: SupportedLanguage): String {
        return when (language) {
            SupportedLanguage.BANGLA -> "হ্যালো! আমি সোরা চান 🌸 কেমন আছো?"
            SupportedLanguage.HINDI -> "नमस्ते! मैं सोरा चान हूँ 🌸 कैसे हो?"
            SupportedLanguage.ENGLISH -> "Hello! I'm Sora Chan 🌸 How can I help you today?"
            SupportedLanguage.URDU -> "السلام علیکم! میں سورا چان ہوں 🌸"
            SupportedLanguage.JAPANESE -> "こんにちは！ソラちゃんです🌸"
        }
    }
}

enum class SupportedLanguage {
    BANGLA, ENGLISH, HINDI, URDU, JAPANESE
}
