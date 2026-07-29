package com.megaultimatesorachan.ai.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.megaultimatesorachan.ai.ui.components.*
import com.megaultimatesorachan.ai.ui.theme.Background
import com.megaultimatesorachan.ai.ui.theme.Primary
import com.megaultimatesorachan.ai.ui.theme.Surface
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val listState = rememberLazyListState()

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { results -> if (results.values.all { it }) viewModel.onMicPermissionGranted() }

    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) listState.animateScrollToItem(uiState.messages.lastIndex)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sora Chan 🌸") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Surface)
            )
        },
        containerColor = Background
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            Box(Modifier.fillMaxWidth().weight(0.35f).padding(16.dp), contentAlignment = Alignment.Center) {
                SoraAvatar(mood = uiState.mood, modifier = Modifier.size(160.dp))
            }
            StatusIndicator(status = uiState.status, modifier = Modifier.align(Alignment.CenterHorizontally))
            LazyColumn(state = listState, modifier = Modifier.weight(0.45f).fillMaxWidth().padding(horizontal = 8.dp)) {
                items(uiState.messages, key = { it.id }) { msg ->
                    ChatBubble(text = msg.text, isUser = msg.isUser)
                }
            }
            Row(modifier = Modifier.fillMaxWidth().background(Surface).padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = uiState.inputText,
                    onValueChange = viewModel::onInputChange,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type or speak…") },
                    shape = RoundedCornerShape(24.dp),
                    maxLines = 3
                )
                Spacer(Modifier.width(8.dp))
                IconButton(onClick = { viewModel.sendText() }, enabled = uiState.inputText.isNotBlank()) {
                    Icon(Icons.Default.Send, "Send", tint = Primary)
                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.Center) {
                Button(onClick = {
                    val perms = mutableListOf(Manifest.permission.RECORD_AUDIO)
                    if (Build.VERSION.SDK_INT >= 33) perms.add(Manifest.permission.POST_NOTIFICATIONS)
                    val need = perms.filter { ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED }
                    if (need.isEmpty()) viewModel.toggleListening() else permissionLauncher.launch(need.toTypedArray())
                }, colors = ButtonDefaults.buttonColors(containerColor = Primary)) {
                    Text(if (uiState.status == SoraStatus.LISTENING) "Stop 🎤" else "Speak 🎤")
                }
            }
        }
    }
}
