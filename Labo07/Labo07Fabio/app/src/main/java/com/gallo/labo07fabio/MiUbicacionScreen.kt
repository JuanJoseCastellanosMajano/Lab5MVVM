package com.gallo.labo07fabio

import android.Manifest
import android.content.Context
import android.location.Location
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MiUbicacionScreen(fusedLocationClient: FusedLocationProviderClient) {

    // Creacion del contexto
    val context = LocalContext.current
    // Estado de permisos
    val fineAccessPermises = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val coarseAccessPermises = rememberPermissionState(Manifest.permission.ACCESS_COARSE_LOCATION)
    // Texto para mostrar la ubicacion
    var locationText = remember { mutableStateOf("Presiona el botón para obtener la ubicación.") }

    // Solicitar permisos de ubicación al iniciar la pantalla
    LaunchedEffect(Unit) {
        if (!coarseAccessPermises.status.isGranted) {
            coarseAccessPermises.launchPermissionRequest() // Solicitar permisos de ubicación
        } else if (!fineAccessPermises.status.isGranted) {
            fineAccessPermises.launchPermissionRequest() // Solicitar permisos de ubicación
        }else{
            // Si los permisos ya están concedidos, obtener la ubicación actual
            obtenerUbicacionActual(context, fusedLocationClient){
                lat, lon ->
                locationText.value = if (lat != null && lon != null) {
                    "Latitud: $lat\nLongitud: $lon"
                } else {
                    "No se pudo obtener la ubicación."
                }
            }
        }
    }

    // Composición de la UI
    Column(
        modifier = Modifier.padding(30.dp)
    ) {
        Text( text = locationText.value)
        Button(onClick = {
            // Solicitar permisos de ubicación al presionar el botón
            if (!coarseAccessPermises.status.isGranted) {
                coarseAccessPermises.launchPermissionRequest()
            } else if (!fineAccessPermises.status.isGranted) {
                fineAccessPermises.launchPermissionRequest()
            }
            // Si los permisos ya están concedidos, obtener la ubicación actual
            else{
                locationText.value = "Permiso concedido. Obteniendo ubicación..."

                obtenerUbicacionActual(context, fusedLocationClient) { lat, lon ->
                    locationText.value = if (lat != null && lon != null) {
                        "Latitud: $lat\nLongitud: $lon"
                    } else {
                        "No se pudo obtener la ubicación."
                    }
            }
        }
        }) {
            Text(text = "Obtener ubicación")
        }
    }
}

private fun obtenerUbicacionActual(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    onLocationResult: (Double?, Double?) -> Unit
) {

    val cancellationTokenSource = CancellationTokenSource()

    fusedLocationClient.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        cancellationTokenSource.token
    )
        .addOnSuccessListener { location: Location? ->
        // Manejo de la ubicación obtenida
        if (location != null) {
            onLocationResult(location.latitude, location.longitude)
        } else {
            onLocationResult(null, null)
            Toast.makeText(context, "No se pudo obtener la ubicación (null).", Toast.LENGTH_SHORT).show()
        }
    }
        .addOnFailureListener { exception ->
        // Manejo de errores al obtener la ubicación
        onLocationResult(null, null)
        Toast.makeText(context, "Error al obtener ubicación: ${exception.message}", Toast.LENGTH_LONG).show()
    }

}