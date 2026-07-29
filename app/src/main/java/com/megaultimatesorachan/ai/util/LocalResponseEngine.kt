package com.megaultimatesorachan.ai.util

import com.megaultimatesorachan.ai.ai.SupportedLanguage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalResponseEngine @Inject constructor() {
    fun handle(input: String, language: SupportedLanguage): String? {
        val lower = input.lowercase()
        return when {
            lower.matches(Regex(".*(hi|hello|hey|হ্যালো|হাই|नमस्ते).*")) -> when (language) {
                SupportedLanguage.BANGLA -> "হ্যালো! আমি সোরা চান 🌸 কেমন আছো?"
                SupportedLanguage.HINDI -> "नमस्ते! मैं सोरा चान हूँ 🌸"
                else -> "Hello! I'm Sora Chan 🌸 How can I help you?"
            }
            lower.matches(Regex(".*(thank|thanks|ধন্যবাদ|धन्यवाद).*")) -> "You're welcome! 😊🌸"
            lower.matches(Regex(".*(bye|goodbye|বিদায়).*")) -> "Bye bye! Take care 🌸"
            lower.matches(Regex(".*(help|সাহায্য).*")) -> "I can control flashlight, volume, open apps, tell jokes, check time and chat with AI! Just ask ✨"
            else -> null
        }
    }
    fun getOfflineFallback(language: SupportedLanguage): String = when (language) {
        SupportedLanguage.BANGLA -> "ইন্টারনেট পাচ্ছি না 😅 কিন্তু আমি এখনো তোমার সাথে কথা বলতে পারি 🌸"
        else -> "No internet 😅 But I can still chat offline with you 🌸"
    }
}
