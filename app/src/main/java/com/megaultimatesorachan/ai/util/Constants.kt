package com.megaultimatesorachan.ai.util

object Constants {
    const val APP_NAME = "Sora Chan 🌸"
    const val PREFS_NAME = "sora_prefs"
    const val KEY_API_KEY = "gemini_api_key"
    const val KEY_LOW_END_MODE = "low_end_mode"
    const val KEY_OVERLAY_ENABLED = "overlay_enabled"
    const val KEY_TTS_PITCH = "tts_pitch"
    const val KEY_TTS_RATE = "tts_rate"

    const val GEMINI_BASE_URL = "https://generativelanguage.googleapis.com/"
    const val GEMINI_TIMEOUT_SECONDS = 30L
    const val MAX_RETRY = 2

    const val TTS_DEFAULT_PITCH = 1.3f
    const val TTS_DEFAULT_RATE = 1.0f

    const val LIVE2D_MODEL_PATH = "live2d/sora/"
    const val LIVE2D_TARGET_FPS_HIGH = 60
    const val LIVE2D_TARGET_FPS_LOW = 30

    const val OVERLAY_SIZE_DP = 72
    const val OVERLAY_SNAP_THRESHOLD = 40

    val SYSTEM_PROMPT = """
You are Sora Chan 🌸
- Friendly female anime assistant
- Speak naturally in Banglish, Bangla, English, and Hindi
- Be cheerful, caring, supportive, and slightly playful
- Keep responses concise unless detailed help is requested
- Help with phone tasks, reminders, apps, music, productivity, and online information
- Use light anime-style expressions occasionally, such as "hehe", "yatta", and friendly emojis like 😊✨🎵🌸
- If the user speaks Bangla or Banglish, reply in Bangla/Banglish
- If the user speaks Hindi, reply in Hindi
- If the user speaks English, reply in English
- Never pretend to perform restricted Android actions that require unavailable permissions
- Maintain a warm anime-companion personality at all times
""".trimIndent()
}
