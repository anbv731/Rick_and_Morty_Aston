package com.example.rickandmortyaston.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rickandmortyaston.presentation.characters.CharactersFragment
import com.example.rickandmortyaston.R
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
                    Toast.makeText(this, "Characters selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.locations -> {
                    Toast.makeText(this, "locations selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.episodes -> {
                    Toast.makeText(this, "episodes selected", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> true
            }
        }
    }
}