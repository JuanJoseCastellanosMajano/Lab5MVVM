package com.JC00026223.labmvvm.presentation.components

import androidx.compose.runtime.Composable
import java.util.Date

data class Card(
    val id: Int,
    val title: String,
    val description: String,
    val endDate: Date,
    val checked: Boolean,
    val onCheckedChange: (Boolean) -> Unit = {}
)

@Composable
fun TaskCard(card: Card) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = card.checked,
                    onCheckedChange = card.onCheckedChange
                )
                Text(
                    text = card.title,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.weight(1f)
                )
            }
            Text(
                text = card.description,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "Fecha límite: ${SimpleDateFormat("dd/MM/yyyy").format(card.endDate)}",
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}