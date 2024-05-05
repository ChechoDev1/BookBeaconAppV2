package com.example.projectbookbeaconv2

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

class LibrosProvider {

    companion object {

        fun obtenerLibrosAleatorios(): List<DatosLibros> {
            return LibrosLista.shuffled()
        }
        // Lista mutable para almacenar los datos de los libros
        val LibrosLista = mutableListOf<DatosLibros>()

        // Función para cargar los datos desde un archivo CSV
        fun cargarLibrosDesdeCSV(context: Context) {
            try {
                // Abre el archivo CSV desde el directorio res/raw
                val inputStream = context.resources.openRawResource(R.raw.libros_imagenes)
                val reader = BufferedReader(InputStreamReader(inputStream))

                // Lee la primera línea (encabezados) y la descarta
                reader.readLine()

                // Lee cada línea del archivo CSV
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    // Divide la línea en partes usando la coma como separador
                    val parts = line!!.split(",")

                    // Añade los datos del libro a la lista de libros
                    LibrosLista.add(
                        DatosLibros(
                            parts[0], // Nombre
                            parts[2], // Género
                            parts[1], // Autor
                            parts[3]  // URL de la imagen
                        )
                    )
                }

                // Cierra el lector
                reader.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Función para buscar libros por nombre, autor o género
        fun buscarLibros(query: String): List<DatosLibros> {
            return LibrosLista.filter { libro ->
                libro.Title.contains(query, ignoreCase = true) ||
                        libro.Author.contains(query, ignoreCase = true) ||
                        libro.genres.contains(query, ignoreCase = true)
            }
        }
    }
}