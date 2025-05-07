package com.JC00026223.labmvvm.presentation.screens


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.JC00026223.labmvvm.presentation.screens.AddTaskScreen
import com.JC00026223.labmvvm.navigation.AppNavigation
import com.JC00026223.labmvvm.presentation.viewmodel.GeneralViewModel
import com.JC00026223.labmvvm.presentation.screens.TODOScreen



@Composable
fun TODOScreen(
    onBack: () -> Unit,
    viewModel: GeneralViewModel = viewModel()
) {
    val tasks = viewModel.tasks.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista TODO") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, "Atrás")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(tasks) { task ->
                TaskItem(task = task)
            }
        }
    }
}