package com.JC00026223.labmvvm.presentation.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

@Composable
fun SensorScreen(onBack: () -> Unit) {
    var selectedSensor by remember { mutableStateOf(Sensor.TYPE_LIGHT) }
    val sensorValues = useSensor(selectedSensor)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sensores") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, "Atrás")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SensorTypeButton(
                    sensorType = Sensor.TYPE_LIGHT,
                    label = "Luz",
                    selected = selectedSensor == Sensor.TYPE_LIGHT,
                    onClick = { selectedSensor = Sensor.TYPE_LIGHT }
                )
                SensorTypeButton(
                    sensorType = Sensor.TYPE_GYROSCOPE,
                    label = "Giroscopio",
                    selected = selectedSensor == Sensor.TYPE_GYROSCOPE,
                    onClick = { selectedSensor = Sensor.TYPE_GYROSCOPE }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            when (selectedSensor) {
                Sensor.TYPE_LIGHT -> LightSensorDisplay(sensorValues)
                Sensor.TYPE_GYROSCOPE -> GyroscopeDisplay(sensorValues)
                else -> Text("Sensor no soportado")
            }
        }
    }
}

@Composable
fun SensorTypeButton(
    sensorType: Int,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.secondary
        )
    ) {
        Text(label)
    }
}

@Composable
fun LightSensorDisplay(values: List<Float>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Sensor de Luz", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Intensidad: ${values.getOrElse(0) { 0f }} lx",
            style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun GyroscopeDisplay(values: List<Float>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Giroscopio", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Eje X: ${values.getOrElse(0) { 0f }}",
            style = MaterialTheme.typography.bodyLarge)
        Text("Eje Y: ${values.getOrElse(1) { 0f }}",
            style = MaterialTheme.typography.bodyLarge)
        Text("Eje Z: ${values.getOrElse(2) { 0f }}",
            style = MaterialTheme.typography.bodyLarge)
    }
}