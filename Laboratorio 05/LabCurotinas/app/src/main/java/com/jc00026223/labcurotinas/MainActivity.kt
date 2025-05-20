package com.jc00026223.labcurotinas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.jc00026223.labcurotinas.data.Libro
import kotlinx.coroutines.delay


import com.jc00026223.labcurotinas.ui.theme.LabCurotinasTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LabCurotinasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Corutina(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Corutina(modifier: Modifier = Modifier) {
    val dataState = remember { mutableStateOf<List<Libro>?>(null) }
    val lifeCycleScope = LocalLifecycleOwner.current.lifecycleScope

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                lifeCycleScope.launch {
                    dataState.value = fetchDataFromDatabase()
                }
            },
            enabled = dataState.value == null
        ) {
            Text(text = if (dataState.value == null) "Get Data" else "Cargando...")
        }

        if (dataState.value == null) {
            Text("")
        } else {
            LazyColumn { //Columna normal no lazy column
                items(dataState.value!!.size) {
                    dataState.value!!.forEach() {  libro ->
                        Card(modifier = Modifier) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Nombre: "+ libro.nombre)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Autor: "+libro.autor)
                                Spacer(Modifier.height(15.dp))
                            }
                        }
                    }

                }
            }
        }
    }
}

suspend fun fetchDataFromDatabase(): List<Libro> {
    delay(3000)
    return listOf(
        Libro("Cien años de soledad", "Gabriel García Márquez"),
        Libro("1984", "George Orwell"),
        Libro("Don Quijote de la Mancha", "Miguel de Cervantes"),
        Libro("Orgullo y prejuicio", "Jane Austen"),
        Libro("Crónica de una muerte anunciada", "Gabriel García Márquez")
    )
}

@Preview
@Composable
fun PreviewMyComposable() {
    Corutina()
}