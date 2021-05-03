package com.example.madlevel5task2.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
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

    private val DATE_PATTERN = Regex(
        "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})\$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))\$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})\$")

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

        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_game, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Return to the Game Backlog Fragment.
                findNavController().popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
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
        val gameReleaseDateInput = binding.etReleaseDate.text.toString()



        if (gameTitleText.isNotBlank() && gamePlatformText.isNotBlank() && gameReleaseDateInput.isNotBlank()) {

            val gameReleaseDateText = getString(R.string.release_date) + " " + gameReleaseDateInput

            val dateFormatter = SimpleDateFormat("dd/MM/yyyy")
            var gameReleaseDate = Date()


            // TODO: Ask teacher how this can be done more efficiently.
            try {
                gameReleaseDate = dateFormatter.parse(binding.etReleaseDate.text.toString())
            } catch(e: Exception) {
                Toast.makeText(activity, R.string.not_valid_game_date, Toast.LENGTH_SHORT).show()
            }



            viewModel.insertGame(Game(gameTitleText, gamePlatformText, gameReleaseDate, gameReleaseDateText))

            // "Pop" the backstack, this means we destroy this fragment and go back to the RemindersFragment.
            findNavController().popBackStack()

        } else if (gameTitleText.isBlank()){
            Toast.makeText(activity, R.string.not_valid_game_title, Toast.LENGTH_SHORT).show()
        } else if (gamePlatformText.isBlank()) {
            Toast.makeText(activity, R.string.not_valid_game_platform, Toast.LENGTH_SHORT).show()
            // TODO: Figure out why regex check is not working.
        } else if (gameReleaseDateInput.isBlank() || DATE_PATTERN.containsMatchIn(gameReleaseDateInput)) {
            Toast.makeText(activity, R.string.not_valid_game_date, Toast.LENGTH_SHORT).show()
        }
    }
}