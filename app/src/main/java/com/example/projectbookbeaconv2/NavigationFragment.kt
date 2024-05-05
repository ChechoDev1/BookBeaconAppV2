package com.example.projectbookbeaconv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.projectbookbeaconv2.databinding.FragmentNavigationBinding

class NavigationFragment : Fragment() {
    private lateinit var binding : FragmentNavigationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNavigationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btHome.setOnClickListener {
            findNavController().navigate(NavigationFragmentDirections.actionNavigationFragmentToHomeFragment())
        }
        binding.btMatch.setOnClickListener {
            findNavController().navigate(NavigationFragmentDirections.actionNavigationFragmentToBookFragment())
        }
        binding.btPerfil.setOnClickListener {
            findNavController().navigate(NavigationFragmentDirections.actionNavigationFragmentToProfileFragment())
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // No realizar ninguna acción al presionar el botón de retroceso
            }
        }

        // Agregar el callback al controlador de retroceso
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }
}