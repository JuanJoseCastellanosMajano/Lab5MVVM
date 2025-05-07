package com.JC00026223.labmvvm.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.JC00026223.labmvvm.presentation.screens.HomeScreen
import com.JC00026223.labmvvm.presentation.screens.TODOScreen
import com.JC00026223.labmvvm.presentation.screens.SensorScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToTodo = { navController.navigate("todo") },
                onNavigateToSensors = { navController.navigate("sensors") }
            )
        }
        composable("todo") {
            TODOScreen(onBack = { navController.popBackStack() })
        }
        composable("sensors") {
            SensorScreen(onBack = { navController.popBackStack() })
        }
    }
}