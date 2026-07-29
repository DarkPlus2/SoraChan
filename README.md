# Sora Chan 🌸

Soft, friendly Android anime AI voice assistant built with **Kotlin + Jetpack Compose**.

## Features

- Multilingual voice (Bangla / Banglish / English / Hindi)
- Gemini AI chat with warm anime personality
- Speech recognition + soft female-style TTS
- Modular command system (system, media, apps, productivity, fun)
- Floating overlay assistant
- Offline fallback responses
- Clean Architecture + MVVM + Hilt
- Dark anime-inspired Material 3 theme
- Live2D integration stubs (ready for Cubism SDK)

## How to add Gemini API key

1. Get a key from [Google AI Studio](https://aistudio.google.com/)
2. Put it in `local.properties`:
   ```
   GEMINI_API_KEY=your_key_here
   ```

## How to add Live2D model

Place your Cubism files here:

```
app/src/main/assets/live2d/sora/
├── sora.model3.json
├── sora.moc3
├── textures/
└── motions/
```

Then implement the real Cubism SDK calls inside `Live2DController` and `Live2DRenderer`.

## Phone-only build notes (Termux)

Modern Compose + AGP 8 projects are best built with Android Studio. Pure Termux builds require a full Android SDK setup and are advanced.

APK location (when successful):
`app/build/outputs/apk/debug/app-debug.apk`

## Required Permissions

- INTERNET
- RECORD_AUDIO
- SYSTEM_ALERT_WINDOW (floating bubble)
- CAMERA / FLASHLIGHT
- MODIFY_AUDIO_SETTINGS
- FOREGROUND_SERVICE
- POST_NOTIFICATIONS

## Soft branding

App name: **Sora Chan 🌸**  
Personality: warm, cheerful, caring anime companion.

Enjoy chatting with Sora Chan!
