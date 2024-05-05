package com.example.projectbookbeaconv2.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectbookbeaconv2.BookRecommendation
import com.example.projectbookbeaconv2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BookRecommendationAdapter(private val books: List<BookRecommendation>) : RecyclerView.Adapter<BookRecommendationAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val authorTextView: TextView = itemView.findViewById(R.id.authorTextView)
        private val genresTextView: TextView = itemView.findViewById(R.id.genresTextView)

        fun bind(book: BookRecommendation) {
            titleTextView.text = book.title
            authorTextView.text = book.author
            genresTextView.text = book.genres
        }

        init {
            itemView.findViewById<Button>(R.id.agregar).setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val book = books[position]
                    // Aquí puedes guardar el libro en Firebase
                    saveBookToFirebase(book)
                }
            }
        }

        private fun saveBookToFirebase(book: BookRecommendation) {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            if (userId != null) {
                val userBooksRef = FirebaseFirestore.getInstance().collection("usuarios").document(userId).collection("libros")
                // Aquí agregamos un nuevo documento con un ID autogenerado
                userBooksRef.add(book)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "Libro guardado correctamente en Firebase con ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error al guardar el libro en Firebase", e)
                    }
            } else {
                Log.e(TAG, "No se pudo obtener el ID del usuario actual")
            }
        }

    }
}