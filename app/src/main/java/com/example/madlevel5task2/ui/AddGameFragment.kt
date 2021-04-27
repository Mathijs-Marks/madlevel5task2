package com.example.madlevel5task2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.madlevel5task2.R
import com.example.madlevel5task2.databinding.FragmentAddGameBinding
import com.example.madlevel5task2.model.Game
import com.example.madlevel5task2.viewmodel.GameViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddGameFragment : Fragment() {

    private var _binding: FragmentAddGameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabSaveGame.setOnClickListener {
            //onAddGame()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*private fun onAddGame() {
        val gameTitleText = binding.etGameTitle.text.toString()
        val gamePlatformText = binding.etGamePlatform.text.toString()
        val gameReleaseDate =

        if (gameTitleText.isNotBlank() && gamePlatformText.isNotBlank() && gameReleaseDate.isNotBlank()) {
            viewModel.insertGame(Game(gameTitleText, gamePlatformText, gameReleaseDate))
        }
    }*/
}