package com.example.projectbookbeaconv2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SwitchCompat
import androidx.navigation.fragment.findNavController
import com.example.projectbookbeaconv2.databinding.FragmentGenresBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class GenresFragment : Fragment() {

    private var _binding: FragmentGenresBinding? = null
    private val binding get() = _binding!!
    private val selectedSwitches: MutableList<SwitchCompat> = mutableListOf()
    private lateinit var firestore: FirebaseFirestore
    private lateinit var userId: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGenresBinding.inflate(inflater, container, false)
        val root = binding.root

        // Inicializar Firebase Firestore
        firestore = FirebaseFirestore.getInstance()
        // Obtener el ID del usuario actual
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        // Agregar los SwitchCompat a la lista de selección
        selectedSwitches.apply {
            add(binding.swGen1)
            add(binding.swGen2)
            add(binding.swGen3)
            add(binding.swGen4)
            add(binding.swGen5)
            add(binding.swGen6)
            add(binding.swGen7)
            add(binding.swGen8)
            add(binding.swGen9)
            add(binding.swGen10)
            add(binding.swGen11)
            add(binding.swGen12)
            add(binding.swGen13)
            add(binding.swGen14)
            add(binding.swGen15)
            add(binding.swGen16)
            add(binding.swGen17)
            add(binding.swGen18)
        }

        selectedSwitches.forEach { switch ->
            switch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // Verificar si el número de switches seleccionados es igual o menor que 5
                    if (selectedSwitches.filter { it.isChecked }.size > 5) {
                        switch.isChecked = false // Desactivar el switch si excede el límite
                    }
                }
            }
        }

        binding.btActualizarGeneros.setOnClickListener {
            val selectedGenres = selectedSwitches
                .filter { it.isChecked }
                .map { switch ->
                    when (switch.id) {
                        R.id.swGen1 -> "Fiction"
                        R.id.swGen2 -> "Classics"
                        R.id.swGen3 -> "Nonfiction"
                        R.id.swGen4 -> "Literature"
                        R.id.swGen5 -> "Fantasy"
                        R.id.swGen6 -> "Novels"
                        R.id.swGen7 -> "Historical"
                        R.id.swGen8 -> "Mystery"
                        R.id.swGen9 -> "Romance"
                        R.id.swGen10 -> "Contemporary"
                        R.id.swGen11 -> "Adventure"
                        R.id.swGen12 -> "Young Adult"
                        R.id.swGen13 -> "Adult"
                        R.id.swGen14 -> "Philosophy"
                        R.id.swGen15 -> "Science Fiction"
                        R.id.swGen16 -> "Childrens"
                        R.id.swGen17 -> "Humor"
                        R.id.swGen18 -> "Thriller"

                        else -> "" // Manejo para switches no mapeados
                    }
                }
            if (selectedGenres.isEmpty()) {
                showAlert("Debes seleccionar al menos un género.")
            } else {
                // Subir los géneros seleccionados a Firestore
                uploadGenresToFirestore(selectedGenres)
                findNavController().navigate(GenresFragmentDirections.actionGenresFragmentToBookFragment())
            }

        }
        binding.imgBack3.setOnClickListener {
            findNavController().navigate(GenresFragmentDirections.actionGenresFragmentToBookFragment())
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // No realizar ninguna acción al presionar el botón de retroceso
            }
        }

        // Agregar el callback al controlador de retroceso
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


        return root
    }

    // Función para mostrar un diálogo de alerta
    private fun showAlert(message: String) {
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton("Aceptar", null)
            .show()
    }

    // Función para subir o actualizar los géneros seleccionados a Firestore
    private fun uploadGenresToFirestore(genres: List<String>) {
        // Unimos los géneros en un solo string separado por coma
        val genresString = genres.joinToString(";")

        // Actualizamos el campo "genres" en el documento del usuario actual en Firestore
        firestore.collection("users").document(userId)
            .update("genres", genresString)
            .addOnSuccessListener {
                // Manejar el éxito de la actualización
                showAlert("Géneros actualizados correctamente.")
                Log.d("Firestore", "Actualización de géneros exitosa.")
            }
            .addOnFailureListener { e ->
                // Manejar el fallo de la actualización
                showAlert("Error al actualizar los géneros: ${e.message}")
                Log.e("Firestore", "Error al actualizar los géneros: ${e.message}", e)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}