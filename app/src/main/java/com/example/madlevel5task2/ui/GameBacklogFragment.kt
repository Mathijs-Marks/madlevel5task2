package com.example.madlevel5task2.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel5task2.R
import com.example.madlevel5task2.databinding.FragmentGameBacklogBinding
import com.example.madlevel5task2.model.Game
import com.example.madlevel5task2.viewmodel.GameViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameBacklogFragment : Fragment() {

    // Initialize binding for the Game Backlog.
    private var _binding: FragmentGameBacklogBinding? = null
    private val binding get() = _binding!!

    // Initialize the list of games and the adapter.
    private val games = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(games)

    // Initialize the viewModel.
    private val viewModel: GameViewModel by viewModels()

    // Inflate the layout for this fragment.
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        _binding = FragmentGameBacklogBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Setup the actionBar, setup the RecyclerView, get the list of games and put them in the RecyclerView.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.game_backlog_fragment_label)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        initViews()
        observeAddGameResult()
    }

    // Inflate the layout for the deleteAllGames button.
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_game_backlog, menu)
    }

    // Make the deleteAllGames delete all the games.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_all -> {
                // Clear the Game History list.
                onDeleteAllGames()
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

    // Initialize the recycler view with a linear layout manager, adapter.
    private fun initViews() {
        binding.rvGameBacklog.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvGameBacklog.adapter = gameAdapter
        createItemTouchHelper().attachToRecyclerView(binding.rvGameBacklog)
    }

    // Observe the ViewModel for changes in the database.
    private fun observeAddGameResult() {
        viewModel.games.observe(viewLifecycleOwner, Observer { games ->
            this@GameBacklogFragment.games.clear()
            this@GameBacklogFragment.games.addAll(games)
            gameAdapter.notifyDataSetChanged()
        })
    }

    // Give functionality to screen input.
    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Disable drag and drop functionality.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Delete a game when swiping left.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val gameToDelete = games[position]

                viewModel.deleteGame(gameToDelete)
            }
        }
        return ItemTouchHelper(callback)
    }

    private fun onDeleteAllGames() {

        this@GameBacklogFragment.games.clear()
        gameAdapter.notifyDataSetChanged()

        val undoSnackbar = view?.let { Snackbar.make(it, getString(R.string.all_games_deleted), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.undo)) { observeAddGameResult() }
                .addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(snackbar: Snackbar, event: Int) {
                        if (event == DISMISS_EVENT_TIMEOUT) {
                            viewModel.deleteAllGames()
                            gameAdapter.notifyDataSetChanged()
                        }
                    }
                })
        }
        undoSnackbar?.show()
    }
}