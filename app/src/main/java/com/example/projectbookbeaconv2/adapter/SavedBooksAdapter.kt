package com.example.projectbookbeaconv2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectbookbeaconv2.R
import com.example.projectbookbeaconv2.UserBook

class SavedBooksAdapter(private val savedBooksList: List<UserBook>) :
    RecyclerView.Adapter<SavedBooksAdapter.SavedBooksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedBooksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_book, parent, false)
        return SavedBooksViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedBooksViewHolder, position: Int) {
        val book = savedBooksList[position]
        // Aquí vincula los datos del libro con las vistas en el ViewHolder
        holder.bind(book)
    }

    override fun getItemCount(): Int {
        return savedBooksList.size
    }

    class SavedBooksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(book: UserBook) {
            // Aquí asigna los datos del libro a las vistas en el ViewHolder
            itemView.findViewById<TextView>(R.id.titleTextViewProfile).text = book.Title
            itemView.findViewById<TextView>(R.id.authorTextViewProfile).text = book.Author
            itemView.findViewById<TextView>(R.id.genresTextViewProfile).text = book.genres
            // Aquí puedes agregar más lógica según sea necesario
        }
    }
}