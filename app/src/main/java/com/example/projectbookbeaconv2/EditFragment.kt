package com.example.projectbookbeaconv2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.projectbookbeaconv2.databinding.FragmentEditBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EditFragment : Fragment() {
    private lateinit var binding: FragmentEditBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var uid: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(inflater,container,false)

        // Inicializar FirebaseAuth
        auth = FirebaseAuth.getInstance()

        firestore = FirebaseFirestore.getInstance()

        // Obtener uid
        uid = auth.currentUser?.uid ?: ""

        binding.actualizarDatos.setOnClickListener {
            actualizarDatosUsuario()
        }

        binding.imgBack4.setOnClickListener{
            findNavController().navigate(EditFragmentDirections.actionEditFragmentToProfileFragment())
        }

        return binding.root
    }

    private fun actualizarDatosUsuario() {
        val nuevoUsuario = binding.actualizarUsuario.text.toString().trim()
        val nuevoNombre = binding.actualizarNombre.text.toString().trim()

        val userData: MutableMap<String, Any> = hashMapOf(
            "usuario" to nuevoUsuario,
            "nombre" to nuevoNombre
        )
        uid = auth.currentUser?.uid ?: ""
        firestore.collection("users").document(uid)
            .update(userData)
            .addOnSuccessListener {
                showAlert("Datos actualizados correctamente\nEs posible que no se puedan ver los cambios realizados al instante")
                findNavController().navigate(EditFragmentDirections.actionEditFragmentToProfileFragment())
                Log.d("update", "Todo correcto")
            }
            .addOnFailureListener { e ->
                Log.e("update","Algo salio mal")
            }
    }

    private fun showAlert(message: String) {
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton("Aceptar", null)
            .show()
    }

}