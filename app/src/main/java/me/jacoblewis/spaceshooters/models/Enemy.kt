package me.jacoblewis.spaceshooters.models

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import me.jacoblewis.spaceshooters.gengine.Bounds
import me.jacoblewis.spaceshooters.gengine.Renderable
import me.jacoblewis.spaceshooters.gengine.jiggle
import me.jacoblewis.spaceshooters.gengine.jiggleW
import kotlin.reflect.KMutableProperty0

data class Enemy(
    override var x: Float,
    override var y: Float,
    val radius: Float,
    var speed: Float,
    val hitField: Float = radius * 1.2f
) : Renderable() {
    private val whitePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    lateinit var score: KMutableProperty0<Int>

    override fun initAssets() {
        whitePaint.color = Color.WHITE
        whitePaint.style = Paint.Style.FILL
        bounds = Bounds.CircularBound(radius)
    }

    override fun drawNow(canvas: Canvas?, fps: Long) {
        canvas?.drawCircle(x.vx, y.vy, radius.vs, whitePaint)

        y += speed
        if (y > vHeight) {
            removeFromScreen()
        }
        val rayHit = basicHitTestByType<Ray>().firstOrNull()
        if (rayHit != null) {
            // Enemy Hit!!!
            rayHit.removeFromScreen()
            score.set(score.get() + 1)

            if (radius > 25f) {
                val en1 = Enemy(x - jiggle(radius), y + jiggleW(radius / 2), radius / 2, speed)
                en1.score = score
                val en2 = Enemy(x + jiggle(radius), y + jiggleW(radius / 2), radius / 2, speed)
                en2.score = score
                // Add two new smaller enemies to the screen
                addToScreen(en1)
                addToScreen(en2)
            }
            removeFromScreen()
        }
    }

}