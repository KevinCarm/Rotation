package com.example.rotation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var gravity: Sensor? = null
    private lateinit var image: ImageView
    private lateinit var porte1: Button
    private lateinit var porte2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        porte1 = findViewById(R.id.button)
        porte2 = findViewById(R.id.button3)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        image = findViewById(R.id.imageBall)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    private var gol: Int = 0
    override fun onSensorChanged(event: SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_GRAVITY -> {
                val text =
                    "Location : X ${event.values[0]} Y ${event.values[1]} Z ${event.values[2]}"
                Log.d("POS", "X: ${image.x} Y: ${image.y}")
                if((image.x >=porte1.x && image.x < porte1.x +100 ) && (image.y >= porte1.y &&image.y < porte1.y + 100)){
                    porte1.text = (porte1.text.toString().toInt() + 1).toString()
                }
                if((image.x >=porte2.x && image.x < porte2.x +100 ) && (image.y >= porte2.y &&image.y < porte2.y + 100)){
                    porte2.text = (porte2.text.toString().toInt() + 1).toString()
                }
                if (event.values[0] < 0) {
                    if (image.x < 1000) {
                        image.x += 25;
                        //setContentView(Vista(this,image.x.toInt(),image.y.toInt(),2 ))
                    }
                } else if (event.values[0] > 0) {
                    if (image.x > 0) {
                        image.x -= 25;
                        //setContentView(Vista(this,image.x.toInt(),image.y.toInt(),2))
                    }
                }
                if (event.values[1] < 0) {
                    if(image.y > 0){
                        image.y -= 25;
                        //setContentView(Vista(this,image.x.toInt(),image.y.toInt(),2))
                    }
                } else if (event.values[1] > 0) {
                    if(image.y < 1600){
                        image.y += 25;
                        //setContentView(Vista(this,image.x.toInt(),image.y.toInt(),2))
                    }
                }
                image.refreshDrawableState()
                Log.d("SENSOR", text)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        sensorManager
            .registerListener(
                this,
                gravity,
                SensorManager.SENSOR_DELAY_UI
            )
    }

    override fun onPause() {
        super.onPause()
        sensorManager
            .unregisterListener(this, gravity)
    }
}