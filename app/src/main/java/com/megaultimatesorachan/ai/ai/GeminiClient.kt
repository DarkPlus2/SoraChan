package com.megaultimatesorachan.ai.ai

import com.megaultimatesorachan.ai.BuildConfig
import com.megaultimatesorachan.ai.util.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

interface GeminiApi {
    @POST("v1beta/models/{model}:generateContent")
    suspend fun generateContent(
        @retrofit2.http.Path("model") model: String,
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse
}

@Singleton
class GeminiClient @Inject constructor() {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }

    private val okHttp = OkHttpClient.Builder()
        .connectTimeout(Constants.GEMINI_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(Constants.GEMINI_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(Constants.GEMINI_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.GEMINI_BASE_URL)
        .client(okHttp)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val api = retrofit.create(GeminiApi::class.java)

    private val systemInstruction = Content(
        parts = listOf(Part(text = Constants.SYSTEM_PROMPT))
    )

    suspend fun chat(
        userMessage: String,
        history: List<ChatMessage> = emptyList(),
        apiKey: String = BuildConfig.GEMINI_API_KEY
    ): Result<String> = withContext(Dispatchers.IO) {
        if (apiKey.isBlank() || apiKey == "PASTE_GEMINI_API_KEY_HERE") {
            return@withContext Result.failure(IllegalStateException("Gemini API key not set"))
        }

        val contents = mutableListOf<Content>()
        history.takeLast(10).forEach { msg ->
            contents.add(
                Content(
                    role = if (msg.isUser) "user" else "model",
                    parts = listOf(Part(text = msg.text))
                )
            )
        }
        contents.add(Content(role = "user", parts = listOf(Part(text = userMessage))))

        val request = GeminiRequest(
            contents = contents,
            systemInstruction = systemInstruction,
            generationConfig = GenerationConfig()
        )

        var lastError: Exception? = null
        repeat(Constants.MAX_RETRY + 1) { attempt ->
            try {
                val response = api.generateContent(
                    model = BuildConfig.GEMINI_MODEL,
                    apiKey = apiKey,
                    request = request
                )
                if (response.error != null) {
                    throw Exception(response.error.message ?: "Gemini API error")
                }
                val text = response.candidates
                    ?.firstOrNull()
                    ?.content
                    ?.parts
                    ?.firstOrNull()
                    ?.text
                    ?.trim()
                if (text.isNullOrBlank()) {
                    throw Exception("Empty response from Gemini")
                }
                return@withContext Result.success(text)
            } catch (e: Exception) {
                lastError = e
                if (attempt < Constants.MAX_RETRY) {
                    delay(500L * (attempt + 1))
                }
            }
        }
        Result.failure(lastError ?: Exception("Unknown Gemini error"))
    }
}
