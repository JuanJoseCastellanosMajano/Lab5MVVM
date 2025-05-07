package com.JC00026223.labmvvm.presentation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun useSensor(sensorType: Int): List<Float> {
    val context = LocalContext.current
    val sensorManager = remember {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    val sensor = sensorManager.getDefaultSensor(sensorType) ?: return emptyList()
    var sensorValues by remember { mutableStateOf(listOf(0f, 0f, 0f)) }

    DisposableEffect(sensorType) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.values?.let {
                    sensorValues = it.toList()
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(
            listener,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    return sensorValues
}