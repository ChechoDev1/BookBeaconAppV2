package com.example.projectbookbeaconv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.projectbookbeaconv2.R
import com.example.projectbookbeaconv2.databinding.FragmentRegister1Binding


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegister1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegister1Binding.inflate(inflater, container, false)
        val root1 = binding.root

        binding.imgBack.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragment1ToLoginFragment())
        }


        binding.btBotonSiguiente.setOnClickListener {
            val nombre = binding.etRegisterName.text.toString().trim()
            val correo = binding.etRegisterEmail.text.toString().trim()

            if (nombre.isEmpty()) {
                binding.etRegisterName.error = "Ingrese un nombre"
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                binding.etRegisterEmail.error = "Ingrese un correo válido"
                return@setOnClickListener
            }

            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragment1ToRegisterFragment2(
                nombre = nombre,
                correo = correo))
        }

        return root1
    }
}