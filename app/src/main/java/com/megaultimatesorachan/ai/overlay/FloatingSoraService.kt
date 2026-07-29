package com.megaultimatesorachan.ai.overlay
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FloatingSoraService : Service() {
    private var windowManager: WindowManager? = null
    private var floatingView: ImageView? = null
    override fun onBind(intent: Intent?): IBinder? = null
    override fun onCreate() {
        super.onCreate()
        startForeground(1001, createNotification())
        // Simple placeholder overlay - expand later
    }
    private fun createNotification(): Notification {
        val channelId = "sora_overlay"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getSystemService(NotificationManager::class.java).createNotificationChannel(
                NotificationChannel(channelId, "Sora Chan", NotificationManager.IMPORTANCE_LOW)
            )
        }
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Sora Chan is here 🌸")
            .setContentText("Floating assistant")
            .setSmallIcon(android.R.drawable.ic_btn_speak_now)
            .setOngoing(true).build()
    }
    override fun onDestroy() {
        floatingView?.let { windowManager?.removeView(it) }
        super.onDestroy()
    }
}
