package com.megaultimatesorachan.ai.commands

import android.content.Context
import android.content.Intent
import android.hardware.camera2.CameraManager
import android.media.AudioManager
import android.os.BatteryManager
import android.os.Build
import android.provider.Settings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SystemCommands @Inject constructor() {
    private var torchOn = false
    fun handle(input: String, context: Context): String? {
        return when {
            input.contains("flashlight") || input.contains("torch") || input.contains("ফ্ল্যাশ") -> toggleTorch(context)
            input.contains("volume up") || input.contains("ভলিউম বাড়াও") -> adjustVolume(context, true)
            input.contains("volume down") || input.contains("ভলিউম কমাও") -> adjustVolume(context, false)
            input.contains("battery") || input.contains("ব্যাটারি") -> batteryStatus(context)
            input.contains("open settings") || input.contains("সেটিংস") -> {
                openSettings(context, Settings.ACTION_SETTINGS); "Opening Settings 🌸"
            }
            input.contains("wifi") -> { openSettings(context, Settings.ACTION_WIFI_SETTINGS); "Opening Wi-Fi ✨" }
            else -> null
        }
    }
    private fun toggleTorch(context: Context): String = try {
        val cm = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val id = cm.cameraIdList.firstOrNull() ?: return "No camera"
        torchOn = !torchOn; cm.setTorchMode(id, torchOn)
        if (torchOn) "Flashlight on! 💡" else "Flashlight off 🌙"
    } catch (e: Exception) { "Could not control flashlight" }
    private fun adjustVolume(context: Context, up: Boolean): String {
        val am = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        am.adjustStreamVolume(AudioManager.STREAM_MUSIC, if (up) AudioManager.ADJUST_RAISE else AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI)
        return if (up) "Volume up 🔊" else "Volume down 🔉"
    }
    private fun batteryStatus(context: Context): String {
        val bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        val level = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        return "Battery is at $level% 🌸"
    }
    private fun openSettings(context: Context, action: String) {
        try { context.startActivity(Intent(action).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) } catch (_: Exception) {}
    }
}
