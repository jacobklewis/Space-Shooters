package me.jacoblewis.spaceshooters.models

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import me.jacoblewis.spaceshooters.gengine.Renderable
import me.jacoblewis.spaceshooters.gengine.translate

data class Ray(val startX: Float, var yPos: Float) :
    Renderable() {
    private val accentPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rayPath = Path()
    private val raySpeed = 14


    override fun initAssets() {
        accentPaint.color = Color.YELLOW
        accentPaint.strokeWidth = 5.vs
        accentPaint.style = Paint.Style.STROKE
        createPath()
    }

    private fun createPath() {
        with(rayPath) {
            moveTo(0.vx, 0.vy)
            lineTo(0.vx, 7.vy)
        }
    }

    override fun drawNow(canvas: Canvas?, fps: Long) {
        val normalizedMult = 60 / fps
        yPos -= raySpeed * normalizedMult
        canvas?.drawPath(rayPath.translate(startX.vx, yPos.vy), accentPaint)
        if (yPos < 0f) {
            removeFromScreen()
        }
    }
}