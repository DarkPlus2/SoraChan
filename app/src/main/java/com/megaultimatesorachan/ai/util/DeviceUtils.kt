package com.megaultimatesorachan.ai.util
import android.app.ActivityManager
import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class DeviceUtils @Inject constructor() {
    fun isLowEndDevice(context: Context): Boolean {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val mem = ActivityManager.MemoryInfo()
        am.getMemoryInfo(mem)
        return mem.totalMem / (1024.0 * 1024 * 1024) < 3.0
    }
}
