package me.jacoblewis.spaceshooters.models

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import me.jacoblewis.spaceshooters.gengine.Renderable

class Scoreboard(var score: Int) : Renderable() {
    private val accentPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun initAssets() {
        accentPaint.color = Color.WHITE
        accentPaint.style = Paint.Style.FILL
        accentPaint.textSize = 50.vs
    }

    override fun drawNow(canvas: Canvas?, fps: Long) {
        canvas?.drawText("Score: $score", 10.vx, 70.vy, accentPaint)
    }

}