package com.JuanCastellanos.labo07ubicacion

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


    val context = LocalContext.current
    val fineAccessPermises = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val coarseAccessPermises = rememberPermissionState(Manifest.permission.ACCESS_COARSE_LOCATION)
    var locationText = remember { mutableStateOf("Presiona el botón para obtener la ubicación.") }

    LaunchedEffect(Unit) {
        if (!coarseAccessPermises.status.isGranted) {
            coarseAccessPermises.launchPermissionRequest()
        } else if (!fineAccessPermises.status.isGranted) {
            fineAccessPermises.launchPermissionRequest()
        }else{

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


    Column(
        modifier = Modifier.padding(30.dp)
    ) {
        Text( text = locationText.value)
        Button(onClick = {

            if (!coarseAccessPermises.status.isGranted) {
                coarseAccessPermises.launchPermissionRequest()
            } else if (!fineAccessPermises.status.isGranted) {
                fineAccessPermises.launchPermissionRequest()
            }

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

        if (location != null) {
            onLocationResult(location.latitude, location.longitude)
        } else {
            onLocationResult(null, null)
            Toast.makeText(context, "No se pudo obtener la ubicación (null).", Toast.LENGTH_SHORT).show()
        }
    }
        .addOnFailureListener { exception ->
        onLocationResult(null, null)
        Toast.makeText(context, "Error al obtener ubicación: ${exception.message}", Toast.LENGTH_LONG).show()
    }

}