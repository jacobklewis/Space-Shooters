package me.jacoblewis.spaceshooters.models

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import me.jacoblewis.spaceshooters.gengine.Renderable
import kotlin.math.pow

data class Enemy(
    val xPos: Float,
    var yPos: Float,
    val radius: Float,
    var speed: Float,
    val hitField: Float = radius * 1.2f
) : Renderable() {
    private val whitePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun initAssets() {
        whitePaint.color = Color.WHITE
        whitePaint.style = Paint.Style.FILL
    }

    override fun drawNow(canvas: Canvas?, fps: Long) {
        canvas?.drawCircle(xPos.vx, yPos.vy, radius.vs, whitePaint)

        yPos += speed
        if (yPos > vHeight) {
            removeFromScreen()
        }
        val rays: List<Ray> = findRenderablesByType()
        for (ray in rays) {
            if ((ray.startX - xPos).pow(2) + (ray.yPos - yPos).pow(2) < hitField.pow(2)) {
                removeFromScreen()
                ray.removeFromScreen()
                break
            }
        }
    }

}