package com.example.madlevel5task2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.madlevel5task2.R
import com.example.madlevel5task2.databinding.ActivityMainBinding

/**
 * This class is responsible for setting up the main UI components of the app.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        binding.fab.setOnClickListener {
            navController.navigate(
                R.id.action_gameBacklogFragment_to_addGameFragment
            )
        }

        fabToggler()
    }

    // Turn off the fab when not in the AddGameFragment.
    private fun fabToggler() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in arrayOf(R.id.addGameFragment)) {
                binding.fab.hide()
            } else {
                binding.fab.show()
            }
        }
    }
}