package me.jacoblewis.spaceshooters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.View
import me.jacoblewis.spaceshooters.gengine.VirtualScreen
import me.jacoblewis.spaceshooters.models.Backdrop
import me.jacoblewis.spaceshooters.models.Enemy
import me.jacoblewis.spaceshooters.models.Spaceship
import kotlin.math.max

class GameScene(context: Context) : SurfaceView(context), View.OnTouchListener {

    private val vScreen = VirtualScreen(720, 1280, VirtualScreen.FILL_ASPECT, this)
    private val backdrop = Backdrop()
    private val spaceship = Spaceship()
    private var counter = 0

    init {
        setZOrderOnTop(true)
        setBackgroundColor(Color.BLACK)
        setOnTouchListener(this)
        vScreen.addRenderable(backdrop)
        vScreen.addRenderable(spaceship)
        vScreen.setOnFrameListener {
            if (counter++ % max(30, (Math.random() * 120).toInt()) == 0) {
                vScreen.addRenderable(
                    Enemy(
                        Math.random().toFloat() * vScreen.width(),
                        0f,
                        max(10f, Math.random().toFloat() * 50f),
                        1f
                    )
                )
            }
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event?.actionMasked == MotionEvent.ACTION_DOWN) {
            spaceship.fire()
            Log.i("FIRE", "Now")
        }
        return true
    }

    fun horizAcc(fl: Float) {
        spaceship.move(fl * 3)
    }

}