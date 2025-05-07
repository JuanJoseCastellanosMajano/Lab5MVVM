package com.JC00026223.labmvvm.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.util.Date

@Composable
fun AddTaskScreen(
    viewModel: GeneralViewModel,
    onBack: () -> Unit
) {
    val newTitle = remember { mutableStateOf("") }
    val newDescription = remember { mutableStateOf("") }

    Column {
        TextField(
            value = newTitle.value,
            onValueChange = { newTitle.value = it },
            label = { Text("Título") }
        )

        TextField(
            value = newDescription.value,
            onValueChange = { newDescription.value = it },
            label = { Text("Descripción") }
        )

        Button(onClick = {
            val newTask = Task(
                id = (0..1000).random(), // ID temporal
                title = newTitle.value,
                description = newDescription.value,
                endDate = Date(),
                isCompleted = false
            )
            viewModel.addTask(newTask)
            onBack()
        }) {
            Text("Guardar Tarea")
        }
    }
}