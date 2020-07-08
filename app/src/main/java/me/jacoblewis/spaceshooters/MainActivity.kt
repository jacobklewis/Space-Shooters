package me.jacoblewis.spaceshooters

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var scene: GameScene

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scene = GameScene(this)
        setContentView(scene)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val sensorMan = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorMan.getDefaultSensor(Sensor.TYPE_GRAVITY) as? Sensor
            ?: throw Exception("Gravity Sensor Not Available")
        sensorMan.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // ignore
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return
//        Log.i("GRAVITY", "x: ${event.values[0]}, y: ${event.values[1]}, z: ${event.values[2]}")
        scene.horizAcc(event.values[0] * -1)
    }
}