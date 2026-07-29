package com.megaultimatesorachan.ai.live2d
import android.view.View
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class Live2DRenderer @Inject constructor(private val controller: Live2DController) {
    fun createView(): View? = null
    fun onResume() {}
    fun onPause() {}
    fun onDestroy() { controller.release() }
}
