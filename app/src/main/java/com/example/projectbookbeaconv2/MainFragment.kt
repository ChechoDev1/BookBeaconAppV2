package com.example.projectbookbeaconv2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.projectbookbeaconv2.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class MainFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private lateinit var auth: FirebaseAuth
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root = binding.root



        val auth = FirebaseAuth.getInstance()

        // Acceder al botón btRegister
        binding.btRegister.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionLoginFragmentToRegisterFragment1())
        }


        binding.btBotonLogin.setOnClickListener {
            val correo = binding.etLoginEmail.text.toString()
            val contrasena = binding.etPasswordUsername.text.toString()

            if (correo.isEmpty() || contrasena.isEmpty()) {
                // Mostrar un mensaje al usuario indicando que debe completar todos los campos
                Toast.makeText(context, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Salir del método onClick sin intentar iniciar sesión
            }

            auth.signInWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // El usuario ha iniciado sesión correctamente
                        Log.d("usuario","Ingreso exitoso")
                        val user = auth.currentUser
                        findNavController().navigate(MainFragmentDirections.actionLoginFragmentToNavigationFragment())
                    } else {
                        // El inicio de sesión ha fallado, muestra un mensaje de error al usuario
                        Toast.makeText(context, "Error al iniciar sesión. Por favor, verifica tus credenciales.", Toast.LENGTH_SHORT).show()
                    }
                }
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}