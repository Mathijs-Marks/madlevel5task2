package com.example.madlevel5task2.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.madlevel5task2.R
import com.example.madlevel5task2.databinding.FragmentAddGameBinding
import com.example.madlevel5task2.model.Game
import com.example.madlevel5task2.viewmodel.GameViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.Date

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddGameFragment : Fragment() {

    // Initialize binding for the Game Backlog.
    private var _binding: FragmentAddGameBinding? = null
    private val binding get() = _binding!!

    // Initialize the viewModel.
    private val viewModel: GameViewModel by viewModels()

    // Inflate the layout for this fragment.
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Setup the actionBar, add functionality to the fab.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.add_game_fragment_label)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(
            R.drawable.abc_vector_test
        )
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.fabSaveGame.setOnClickListener {
            onAddGame()
        }
    }

    // Safely switch between fragments.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Take the input from the user and add a new entry to the database.
    // Return to the last fragment when done.
    private fun onAddGame() {
        val gameTitleText = binding.etGameTitle.text.toString()
        val gamePlatformText = binding.etGamePlatform.text.toString()
        val gameReleaseDateText = binding.etReleaseDate.text.toString()

        val dateFormatter = SimpleDateFormat("dd/MM/yyyy")
        val gameReleaseDate = dateFormatter.parse(binding.etReleaseDate.text.toString())


        if (gameTitleText.isNotBlank() && gamePlatformText.isNotBlank() && gameReleaseDateText.isNotBlank()) {
            viewModel.insertGame(Game(gameTitleText, gamePlatformText, gameReleaseDate))

            // "Pop" the backstack, this means we destroy this fragment and go back to the RemindersFragment.
            findNavController().popBackStack()
        } else {
            Toast.makeText(activity, R.string.not_valid_game, Toast.LENGTH_SHORT).show()
        }
    }
}