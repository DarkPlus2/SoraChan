package com.megaultimatesorachan.ai.voice

import com.megaultimatesorachan.ai.ai.SupportedLanguage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageDetector @Inject constructor() {
    fun detectLanguage(text: String): SupportedLanguage {
        return when {
            text.matches(Regex(".*[অ-হ].*")) -> SupportedLanguage.BANGLA
            text.matches(Regex(".*[ऀ-ॿ].*")) -> SupportedLanguage.HINDI
            text.matches(Regex(".*[ا-ے].*")) -> SupportedLanguage.URDU
            text.matches(Regex(".*[ぁ-んァ-ン一-龯].*")) -> SupportedLanguage.JAPANESE
            else -> SupportedLanguage.ENGLISH
        }
    }
    fun toLocaleTag(lang: SupportedLanguage): String = when (lang) {
        SupportedLanguage.BANGLA -> "bn-BD"
        SupportedLanguage.ENGLISH -> "en-US"
        SupportedLanguage.HINDI -> "hi-IN"
        SupportedLanguage.URDU -> "ur-PK"
        SupportedLanguage.JAPANESE -> "ja-JP"
    }
}
