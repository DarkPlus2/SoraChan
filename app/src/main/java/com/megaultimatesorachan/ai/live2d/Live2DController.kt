package com.megaultimatesorachan.ai.live2d
import com.megaultimatesorachan.ai.ai.SoraMood
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class Live2DController @Inject constructor() {
    fun loadModel(path: String = "live2d/sora/sora.model3.json") = false
    fun setExpression(expression: SoraMood) {}
    fun updateLipSync(amplitude: Float) {}
    fun setSpeaking(speaking: Boolean) {}
    fun playMotion(name: String) {}
    fun release() {}
}
