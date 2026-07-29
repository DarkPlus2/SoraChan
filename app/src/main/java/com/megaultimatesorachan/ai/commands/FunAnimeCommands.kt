package com.megaultimatesorachan.ai.commands

import com.megaultimatesorachan.ai.ai.SupportedLanguage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FunAnimeCommands @Inject constructor() {
    fun handle(input: String, language: SupportedLanguage): String? {
        return when {
            input.contains("joke") || input.contains("জোক") -> "Why did the anime girl blush? Because she saw the source code! 😂🌸"
            input.contains("cheer") || input.contains("মন খারাপ") -> "Hey! Everything will be okay 🌸 You are amazing! Yatta! ✨"
            input.contains("good morning") || input.contains("শুভ সকাল") -> "Good morning! ☀️ Have a wonderful day, senpai 🌸"
            input.contains("good night") || input.contains("শুভ রাত্রি") -> "Good night! Sweet dreams 😴🌸"
            input.contains("kawaii") -> "Kawaii desu ne~ ✨ You're making me blush! 😳🌸"
            else -> null
        }
    }
}
