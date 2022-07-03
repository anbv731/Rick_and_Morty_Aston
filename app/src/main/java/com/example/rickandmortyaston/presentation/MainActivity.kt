package com.example.rickandmortyaston.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import com.example.rickandmortyaston.presentation.characters.CharactersFragment
import com.example.rickandmortyaston.R
import com.example.rickandmortyaston.presentation.episodes.EpisodesFragment
import com.example.rickandmortyaston.presentation.locations.LocationsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_RickAndMortyAston)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentPlace, CharactersFragment(), "CharactersFragment")
            .commit()
setupNavigation()
    }

    private fun setupNavigation() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.characters -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentPlace, CharactersFragment(), "CharactersFragment")
                        .commit()
                    true
                }
                R.id.locations -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentPlace, LocationsFragment(), "LocationsFragment")
                        .commit()
                    true
                }
                R.id.episodes -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentPlace, EpisodesFragment(), "EpisodesFragment")
                        .commit()
                    true
                }
                else -> true
            }
        }
    }
}