package com.JC00026223.labmvvm.data.model

import java.util.Date

data class Task {
    data class Task(
        val id: Int,
        val title: String,
        val description: String,
        val endDate: Date = Date(),
        val isCompleted: Boolean = false
    )
}