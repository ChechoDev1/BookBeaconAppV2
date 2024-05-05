package com.example.projectbookbeaconv2

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectbookbeaconv2.adapter.LibrosAdapter
import com.example.projectbookbeaconv2.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LibrosProvider.cargarLibrosDesdeCSV(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView() // Se llama a la función sin pasar la vista



        // Encuentra los botones y agrega un OnClickListener a cada uno
        val botonAccion = binding.Accion
        botonAccion.setOnClickListener {
            val resultados = LibrosProvider.buscarLibros("Action")
            (binding.LibrosView.adapter as LibrosAdapter).actualizarDatos(resultados)
        }

        val botonAventura = binding.Aventura
        botonAventura.setOnClickListener {
            val resultados = LibrosProvider.buscarLibros("Aventura")
            (binding.LibrosView.adapter as LibrosAdapter).actualizarDatos(resultados)
        }

        val botonThriller = binding.Thriller
        botonThriller.setOnClickListener {
            val resultados = LibrosProvider.buscarLibros("thriller")
            (binding.LibrosView.adapter as LibrosAdapter).actualizarDatos(resultados)
        }

        val botonDrama = binding.Drama
        botonDrama.setOnClickListener {
            val resultados = LibrosProvider.buscarLibros("Drama")
            (binding.LibrosView.adapter as LibrosAdapter).actualizarDatos(resultados)
        }

        val botonComedia = binding.Comedia
        botonComedia.setOnClickListener {
            val resultados = LibrosProvider.buscarLibros("Comedy")
            (binding.LibrosView.adapter as LibrosAdapter).actualizarDatos(resultados)
        }

        val botonSuspenso = binding.Suspenso
        botonSuspenso.setOnClickListener {
            val resultados = LibrosProvider.buscarLibros("Suspense")
            (binding.LibrosView.adapter as LibrosAdapter).actualizarDatos(resultados)
        }

        val botonTerror = binding.Terror
        botonTerror.setOnClickListener {
            val resultados = LibrosProvider.buscarLibros("Terror")
            (binding.LibrosView.adapter as LibrosAdapter).actualizarDatos(resultados)
        }

        val botonFiccion = binding.Ficcion
        botonFiccion.setOnClickListener {
            val resultados = LibrosProvider.buscarLibros("Fiction")
            (binding.LibrosView.adapter as LibrosAdapter).actualizarDatos(resultados)
        }



        val searchEditText = binding.SearchText
        searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                val query = v.text.toString()
                val resultados = LibrosProvider.buscarLibros(query)
                // Actualiza tu RecyclerView con los resultados
                (binding.LibrosView.adapter as LibrosAdapter).actualizarDatos(resultados)
                // Limpia el EditText
                v.text = ""
                // Oculta el teclado
                val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                true
            } else {
                false
            }
        }

    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(requireContext())
        val decoration = DividerItemDecoration(requireContext(), manager.orientation)
        binding.LibrosView.layoutManager = manager
        binding.LibrosView.adapter = LibrosAdapter(LibrosProvider.obtenerLibrosAleatorios())
        binding.LibrosView.addItemDecoration(decoration)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar el layout utilizando View Binding
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
}