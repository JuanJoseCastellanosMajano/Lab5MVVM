package com.JC00026223.labmvvm.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onNavigateToTodo: () -> Unit,
    onNavigateToSensors: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onNavigateToTodo,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("Ver Lista TODO")
            }

            Button(
                onClick = onNavigateToSensors,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("Ver Sensores")
            }
        }
    }
}