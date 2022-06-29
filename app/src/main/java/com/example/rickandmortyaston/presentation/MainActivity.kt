package com.example.rickandmortyaston.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rickandmortyaston.presentation.characters.CharactersFragment
import com.example.rickandmortyaston.R
import com.example.rickandmortyaston.presentation.episodes.EpisodesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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
                    Toast.makeText(this, "locations selected", Toast.LENGTH_SHORT).show()
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