package com.megaultimatesorachan.ai.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.megaultimatesorachan.ai.ai.ChatMessage
import com.megaultimatesorachan.ai.ai.PersonalityManager
import com.megaultimatesorachan.ai.ai.SoraMood
import com.megaultimatesorachan.ai.ui.components.SoraStatus
import com.megaultimatesorachan.ai.voice.SpeechRecognizerManager
import com.megaultimatesorachan.ai.voice.TTSManager
import com.megaultimatesorachan.ai.voice.VoiceCommandProcessor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val messages: List<ChatMessage> = emptyList(),
    val inputText: String = "",
    val status: SoraStatus = SoraStatus.IDLE,
    val mood: SoraMood = SoraMood.NORMAL
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val speechManager: SpeechRecognizerManager,
    private val ttsManager: TTSManager,
    private val voiceProcessor: VoiceCommandProcessor,
    private val personalityManager: PersonalityManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        ttsManager.initialize {}
        ttsManager.setSpeakingListener { speaking ->
            _uiState.update {
                it.copy(status = if (speaking) SoraStatus.SPEAKING else SoraStatus.IDLE,
                    mood = if (speaking) SoraMood.SPEAKING else personalityManager.getCurrentMood())
            }
        }
        speechManager.onResult = { text -> viewModelScope.launch { handleUserInput(text) } }
        speechManager.onError = { msg ->
            _uiState.update { it.copy(status = SoraStatus.IDLE) }
            addMessage(ChatMessage(text = "Voice error: $msg", isUser = false))
        }
        speechManager.onListeningStateChanged = { listening ->
            _uiState.update {
                it.copy(status = if (listening) SoraStatus.LISTENING else it.status,
                    mood = if (listening) SoraMood.LISTENING else it.mood)
            }
        }
    }

    fun onInputChange(text: String) { _uiState.update { it.copy(inputText = text) } }

    fun sendText() {
        val text = _uiState.value.inputText.trim()
        if (text.isBlank()) return
        _uiState.update { it.copy(inputText = "") }
        viewModelScope.launch { handleUserInput(text) }
    }

    fun toggleListening() {
        if (_uiState.value.status == SoraStatus.LISTENING) speechManager.stopListening()
        else speechManager.startListening()
    }

    fun onMicPermissionGranted() { speechManager.startListening() }

    private suspend fun handleUserInput(text: String) {
        addMessage(ChatMessage(text = text, isUser = true))
        _uiState.update { it.copy(status = SoraStatus.THINKING, mood = SoraMood.THINKING) }
        val reply = try { voiceProcessor.process(text) } catch (e: Exception) { "Sorry, something went wrong 😅" }
        addMessage(ChatMessage(text = reply, isUser = false))
        _uiState.update {
            it.copy(status = SoraStatus.SPEAKING, mood = personalityManager.detectMoodFromResponse(reply))
        }
        ttsManager.speak(reply)
    }

    private fun addMessage(msg: ChatMessage) {
        _uiState.update { it.copy(messages = it.messages + msg) }
    }

    override fun onCleared() {
        speechManager.release(); ttsManager.shutdown(); super.onCleared()
    }
}
