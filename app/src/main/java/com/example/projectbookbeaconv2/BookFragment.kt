package com.example.projectbookbeaconv2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectbookbeaconv2.adapter.BookRecommendationAdapter
import com.example.projectbookbeaconv2.databinding.FragmentBookBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookFragment : Fragment() {
    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!
    private lateinit var userId: String
    private lateinit var firestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBookBinding.inflate(inflater, container, false) // Inflar el layout con el binding

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // No realizar ninguna acción al presionar el botón de retroceso
            }
        }
        // Agregar el callback al controlador de retroceso
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return binding.root // Devolver la raíz del layout inflado
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el botón para actualizar géneros
        binding.btGeneros.setOnClickListener {
            findNavController().navigate(BookFragmentDirections.actionBookFragmentToGenresFragment())
        }

        // Configurar el botón para actualizar autores
        binding.btAutores.setOnClickListener {
            findNavController().navigate(BookFragmentDirections.actionBookFragmentToAuthorsFragment())
        }

        binding.imgBack3.setOnClickListener{
            findNavController().navigate(BookFragmentDirections.actionBookFragmentToNavigationFragment())
        }

        // Inicializar Firebase Firestore
        firestore = FirebaseFirestore.getInstance()
        // Obtener el ID del usuario actual
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        // Configurar el botón para generar recomendaciones
        binding.btGenerarRecomendacion.setOnClickListener {
            // Realizar la llamada al servicio para obtener las recomendaciones
            val retrofit = Retrofit.Builder()
                .baseUrl("https://bookbeaconapp-api.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(BookApiService::class.java)

            val call = service.getRecommendations(userId)
            call.enqueue(object : Callback<List<BookRecommendation>> {
                override fun onResponse(call: Call<List<BookRecommendation>>, response: Response<List<BookRecommendation>>) {
                    if (response.isSuccessful) {
                        val recommendations = response.body()
                        recommendations?.let {
                            if (it.isNotEmpty()) {
                                // Verificar si el usuario tiene géneros y autores definidos
                                firestore.collection("users").document(userId).get()
                                    .addOnSuccessListener { document ->
                                        if (document != null && document.exists()) {
                                            val generos = document["genres"]
                                            val autores = document["authors"]
                                            if (generos != null && autores != null) {
                                                // Si ambos campos existen, proceder a generar las recomendaciones
                                                val recyclerView: RecyclerView = view.findViewById(R.id.recyclerRecomendation)
                                                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                                                val adapter = recommendations.let { it1 -> BookRecommendationAdapter(it1) }
                                                recyclerView.adapter = adapter
                                            } else {
                                                // Si alguno de los campos no existe, mostrar un mensaje para actualizar preferencias
                                                Toast.makeText(context, "Por favor, actualiza tus preferencias de géneros y autores.", Toast.LENGTH_LONG).show()
                                            }
                                        } else {
                                            // Si el documento no existe, también pedir al usuario que actualice sus preferencias
                                            Toast.makeText(context, "Por favor, completa la información de tu perfil.", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        // Manejar el error de Firestore
                                        Toast.makeText(context, "Error al obtener la información del usuario.", Toast.LENGTH_LONG).show()
                                        Log.e("Firestore", "Error al obtener el documento", exception)
                                    }
                            }
                        }
                        Log.i("recomendacion","todo bien!")

                    } else {
                        Log.e("recomendacion", "Error en la solicitud: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<BookRecommendation>>, t: Throwable) {
                    // Manejar el fallo de la solicitud
                }
            })
        }

    }

}