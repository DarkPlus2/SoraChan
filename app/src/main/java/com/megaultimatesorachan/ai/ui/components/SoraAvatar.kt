package com.megaultimatesorachan.ai.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.megaultimatesorachan.ai.ai.SoraMood
import com.megaultimatesorachan.ai.ui.theme.Primary
import com.megaultimatesorachan.ai.ui.theme.Secondary

@Composable
fun SoraAvatar(mood: SoraMood = SoraMood.NORMAL, modifier: Modifier = Modifier) {
    val emoji = when (mood) {
        SoraMood.HAPPY -> "😊"; SoraMood.BLUSH -> "😳"; SoraMood.THINKING -> "🤔"
        SoraMood.SURPRISED -> "😮"; SoraMood.SAD -> "😢"; SoraMood.EXCITED -> "🤩"
        SoraMood.SLEEPY -> "😴"; SoraMood.LISTENING -> "🎧"; SoraMood.SPEAKING -> "🗣️"
        else -> "🌸"
    }
    Box(
        modifier = modifier.clip(CircleShape).background(Brush.verticalGradient(listOf(Primary.copy(0.8f), Secondary.copy(0.6f)))),
        contentAlignment = Alignment.Center
    ) {
        Text(text = emoji, fontSize = 64.sp, modifier = Modifier.padding(16.dp))
    }
}
