package com.example.projectbookbeaconv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class RegisterFragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_register_2, container, false)
        val imgBack2 = root.findViewById<ImageButton>(R.id.imgBack2)
        val btBotonSiguiente2 = root.findViewById<Button>(R.id.btBotonSiguiente2)
        val etRegisterUsername = root.findViewById<EditText>(R.id.etRegisterUsername)
        val etRegisterPassword = root.findViewById<EditText>(R.id.etRegisterPassword)

        val args:RegisterFragment2Args by navArgs()
        val nombre = args.nombre
        val correo = args.correo


        imgBack2.setOnClickListener {
            findNavController().navigate(RegisterFragment2Directions.actionRegisterFragment2ToRegisterFragment1())
        }

        btBotonSiguiente2.setOnClickListener {

            val usuario = etRegisterUsername.text.toString().trim()
            val contrasena = etRegisterPassword.text.toString().trim()

            if (usuario.isEmpty()) {
                etRegisterUsername.error = "Ingrese un nombre de usuario valido"
                return@setOnClickListener
            }

            if (contrasena.isEmpty()) {
                etRegisterPassword.error = "Ingrese una contraseña valida"
                return@setOnClickListener
            }

            findNavController().navigate(RegisterFragment2Directions.actionRegisterFragment2ToRegisterFragment3(
                nombre = nombre,
                correo = correo,
                usuario = etRegisterUsername.text.toString(),
                contrasena = etRegisterPassword.text.toString()))


        }

        return root
    }
}