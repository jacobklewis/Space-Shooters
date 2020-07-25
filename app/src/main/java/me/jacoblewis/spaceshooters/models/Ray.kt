package me.jacoblewis.spaceshooters.models

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import me.jacoblewis.spaceshooters.gengine.Bounds
import me.jacoblewis.spaceshooters.gengine.Renderable
import me.jacoblewis.spaceshooters.gengine.translate

data class Ray(override var x: Float, override var y: Float) :
    Renderable() {
    private val accentPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rayPath = Path()
    private val raySpeed = 14


    override fun initAssets() {
        accentPaint.color = Color.YELLOW
        accentPaint.strokeWidth = 5.vs
        accentPaint.style = Paint.Style.STROKE
        createPath()
        bounds = Bounds.CircularBound(1.vs)
    }

    private fun createPath() {
        with(rayPath) {
            moveTo(0.vx, 0.vy)
            lineTo(0.vx, 7.vy)
        }
    }

    override fun drawNow(canvas: Canvas?, fps: Long) {
        val normalizedMult = 60 / fps
        y -= raySpeed * normalizedMult
        canvas?.drawPath(rayPath.translate(x.vx, y.vy), accentPaint)
        if (y < 0f) {
            removeFromScreen()
        }
    }
}