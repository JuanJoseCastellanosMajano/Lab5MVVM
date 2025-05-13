package com.jc00026223.labcurotinas.data

data class Libro(val nombre: String, val autor: String)

val listaDeLibros = listOf(
    Libro("Cien años de soledad", "Gabriel García Márquez"),
    Libro("1984", "George Orwell"),
    Libro("Don Quijote de la Mancha", "Miguel de Cervantes"),
    Libro("Orgullo y prejuicio", "Jane Austen"),
    Libro("Crónica de una muerte anunciada", "Gabriel García Márquez")
)
