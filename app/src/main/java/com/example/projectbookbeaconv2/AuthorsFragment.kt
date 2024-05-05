package com.example.projectbookbeaconv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SwitchCompat
import androidx.navigation.fragment.findNavController
import com.example.projectbookbeaconv2.databinding.FragmentAuthorsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthorsFragment : Fragment() {

    private var _binding: FragmentAuthorsBinding? = null
    private val binding get() = _binding!!
    private val selectedSwitches: MutableList<SwitchCompat> = mutableListOf()
    private lateinit var firestore: FirebaseFirestore
    private lateinit var userId: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthorsBinding.inflate(inflater, container, false)
        val root = binding.root

        // Inicializar Firebase Firestore
        firestore = FirebaseFirestore.getInstance()
        // Obtener el ID del usuario actual
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        // Agregar los SwitchCompat a la lista de selección
        selectedSwitches.apply {
            add(binding.swAut1)
            add(binding.swAut2)
            add(binding.swAut3)
            add(binding.swAut4)
            add(binding.swAut5)
            add(binding.swAut6)
            add(binding.swAut7)
            add(binding.swAut8)
            add(binding.swAut9)
            add(binding.swAut10)
            add(binding.swAut11)
            add(binding.swAut12)
            add(binding.swAut13)
            add(binding.swAut14)
            add(binding.swAut15)
            add(binding.swAut16)
            add(binding.swAut17)
            add(binding.swAut18)
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

        binding.btActualizarAutores.setOnClickListener {
            val selectedAuthors = selectedSwitches
                .filter { it.isChecked }
                .map { switch ->
                    when (switch.id) {
                        R.id.swAut1 -> "Stephen King"
                        R.id.swAut2 -> "William Shakespeare"
                        R.id.swAut3 -> "J.R.R. Tolkien"
                        R.id.swAut4 -> "Sandra Brown"
                        R.id.swAut5 -> "P.G. Wodehouse"
                        R.id.swAut6 -> "Roald Dahl"
                        R.id.swAut7 -> "Orson Scott Card"
                        R.id.swAut8 -> "Agatha Christie"
                        R.id.swAut9 -> "Mercedes Lackey"
                        R.id.swAut10 -> "James Patterson"
                        R.id.swAut11 -> "Fyodor Dostoyevsky"
                        R.id.swAut12 -> "Rumiko Takahashi"
                        R.id.swAut13 -> "Margaret Weis"
                        R.id.swAut14 -> "Gabriel García Márquez"
                        R.id.swAut15 -> "C.S. Lewis"
                        R.id.swAut16 -> "Terry Pratchett"
                        R.id.swAut17 -> "Neil Gaiman"
                        R.id.swAut18 -> "Charles Dickens"
                        else -> "" // Manejo para switches no mapeados
                    }
                }
            if (selectedAuthors.isEmpty()) {
                showAlert("Debes seleccionar al menos un género.")
            } else {
                // Subir los géneros seleccionados a Firestore
                uploadAuthorsToFirestore(selectedAuthors)
                findNavController().navigate(AuthorsFragmentDirections.actionAuthorsFragmentToBookFragment())
            }

        }

        binding.imgBack3.setOnClickListener {
            findNavController().navigate(AuthorsFragmentDirections.actionAuthorsFragmentToBookFragment())
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
    private fun showAlert(message: String) {
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton("Aceptar", null)
            .show()
    }
    // Función para subir o actualizar los autores seleccionados a Firestore
    private fun uploadAuthorsToFirestore(authors: List<String>) {
        // Unimos los géneros en un solo string separado por coma
        val genresString = authors.joinToString(";")

        // Actualizamos el campo "autores" en el documento del usuario actual en Firestore
        firestore.collection("users").document(userId)
            .update("authors", genresString)
            .addOnSuccessListener {
                // Manejar el éxito de la actualización
                showAlert("Autores actualizados correctamente.")
            }
            .addOnFailureListener { e ->
                // Manejar el fallo de la actualización
                showAlert("Error al actualizar los autores: ${e.message}")
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}