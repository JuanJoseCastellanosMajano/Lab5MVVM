package com.JC00026223.labmvvm.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GeneralViewModel : ViewModel() {

    private val _tasks = MutableStateFlow<MutableList<Task>>(mutableListOf())


    val tasks: StateFlow<MutableList<Task>> = _tasks.asStateFlow()

    
    fun addTask(task: Task) {
        _tasks.value = _tasks.value.toMutableList().apply { add(task) }
    }


    fun updateTaskCompletion(id: Int, isCompleted: Boolean) {
        _tasks.value = _tasks.value.map { task ->
            if (task.id == id) task.copy(isCompleted = isCompleted) else task
        }.toMutableList()
    }
}
