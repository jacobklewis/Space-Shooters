package me.jacoblewis.spaceshooters.models

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import me.jacoblewis.spaceshooters.gengine.Renderable
import me.jacoblewis.spaceshooters.gengine.translate
import kotlin.math.max
import kotlin.math.min

class Spaceship : Renderable() {

    private val accentPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var xPos = 360f
    private val shipPath = Path()

    override fun initAssets() {
        accentPaint.color = Color.WHITE
        accentPaint.strokeWidth = 5.vs
        accentPaint.style = Paint.Style.STROKE
        createPath()
    }

    private fun createPath() {
        with(shipPath) {
            moveTo(0.vx, 0.vy)
            lineTo(15.vx, 30.vy)
            lineTo(0.vx, 24.vy)
            lineTo((-15).vx, 30.vy)
            close()
        }
    }

    override fun drawNow(canvas: Canvas?, fps: Long) {
        canvas?.drawPath(shipPath.translate(xPos.vx, 1000.vy), accentPaint)
    }

    fun move(xDistance: Float) {
        if (!isReady) {
            return
        }
        xPos += xDistance
        // Keep Spaceship contained on the screen
        xPos = min(max(0f, xPos), vWidth)
    }

    fun fire() {
        addToScreen(Ray(xPos, 1000f))
    }

}