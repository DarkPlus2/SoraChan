package com.megaultimatesorachan.ai.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.megaultimatesorachan.ai.ui.theme.Accent
import com.megaultimatesorachan.ai.ui.theme.Primary
import com.megaultimatesorachan.ai.ui.theme.Secondary
import com.megaultimatesorachan.ai.ui.theme.Success

enum class SoraStatus { IDLE, LISTENING, THINKING, SPEAKING }

@Composable
fun StatusIndicator(status: SoraStatus, modifier: Modifier = Modifier) {
    val (text, color) = when (status) {
        SoraStatus.IDLE -> "Ready" to Success
        SoraStatus.LISTENING -> "Listening…" to Secondary
        SoraStatus.THINKING -> "Thinking…" to Accent
        SoraStatus.SPEAKING -> "Speaking…" to Primary
    }
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.padding(8.dp)) {
        Icon(Icons.Default.Circle, null, tint = color, modifier = Modifier.size(10.dp))
        Text("  $text", style = MaterialTheme.typography.labelLarge)
    }
}
