package com.example.madlevel5task2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameBacklogFragment : Fragment() {

    private var _binding: FragmentGameBacklogBinding? = null
    private val binding get() = _binding!!

    private val games = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(games)

    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGameBacklogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeAddGameResult()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        // Initialize the recycler view with a linear layout manager, adapter.
        binding.rvGameBacklog.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvGameBacklog.adapter = gameAdapter
        createItemTouchHelper().attachToRecyclerView(binding.rvGameBacklog)
    }

    private fun observeAddGameResult() {
        viewModel.games.observe(viewLifecycleOwner, Observer { games ->
            this@GameBacklogFragment.games.clear()
            this@GameBacklogFragment.games.addAll(games)
            gameAdapter.notifyDataSetChanged()
        })
    }

    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val gameToDelete = games[position]

                viewModel.deleteGame(gameToDelete)
            }
        }
        return ItemTouchHelper(callback)
    }
}