package me.jacoblewis.spaceshooters.models

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import me.jacoblewis.spaceshooters.gengine.Renderable

class Backdrop : Renderable() {
    private val blackPaint = Paint()

    override fun initAssets() {
        blackPaint.color = Color.DKGRAY
        blackPaint.style = Paint.Style.FILL
    }

    override fun drawNow(canvas: Canvas?, fps: Long) {
        canvas?.drawRect(0f, 0f, width, height, blackPaint)
    }

}