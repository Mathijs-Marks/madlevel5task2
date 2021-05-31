package com.example.madlevel5task2.ui

import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel5task2.R
import com.example.madlevel5task2.databinding.ItemGameBinding
import com.example.madlevel5task2.model.Game

class GameAdapter(private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    /**
     * This class takes the data from the data model and arranges them in the correct order in the
     * GameBacklogFragment class.
     */

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemGameBinding.bind(itemView)

        fun dataBind(game: Game) {
            binding.tvGameTitle.text = game.gameTitleText
            binding.tvGamePlatform.text = game.gamePlatformText
            binding.tvGameReleaseDate.text = game.gameReleaseDateText
        }
    }

    // When the ViewHolder is created, load the necessary game items.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )
    }

    // The amount of games to put into the RecyclerView is equal to the size of the games list.
    override fun getItemCount(): Int {
        return games.size
    }

    // When the ViewHolder is bound, put the game items into the correct position in the RecyclerView.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBind(games[position])
    }
}