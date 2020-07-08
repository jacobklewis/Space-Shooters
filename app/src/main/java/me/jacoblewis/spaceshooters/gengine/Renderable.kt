package me.jacoblewis.spaceshooters.gengine

import android.graphics.Canvas

abstract class Renderable {
    var isReady: Boolean = false
        private set
    var virtualScreen: VirtualScreen? = null
        set(value) {
            field = value
            initAssets()
            isReady = true
        }

    abstract fun initAssets()
    abstract fun drawNow(canvas: Canvas?, fps: Long)
    open fun computeNow(fps: Long) {
        /* ignore */
    }

    inline fun <reified T : Renderable> findRenderablesByType(): List<T> {
        return virtualScreen?.renderables?.filterIsInstance<T>()?.toList() ?: emptyList()
    }

    fun removeFromScreen() {
        isReady = false
        virtualScreen?.removeRenderable(this)
        virtualScreen = null
    }

    fun addToScreen(renderable: Renderable) {
        virtualScreen?.addRenderable(renderable)
    }

    val Number.vx: Float
        get() = virtualScreen?.realX(this.toFloat()) ?: 0f
    val Number.vy: Float
        get() = virtualScreen?.realY(this.toFloat()) ?: 0f
    val Number.vs: Float
        get() = virtualScreen?.realScale(this.toFloat()) ?: 0f

    val vWidth: Float
        get() = virtualScreen?.width() ?: 0f
    val vHeight: Float
        get() = virtualScreen?.height() ?: 0f

    val width: Float
        get() = virtualScreen?.realX(vWidth) ?: 0f
    val height: Float
        get() = virtualScreen?.realX(vHeight) ?: 0f
}