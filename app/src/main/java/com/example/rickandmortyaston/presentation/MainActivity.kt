package com.example.rickandmortyaston.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rickandmortyaston.presentation.characters.CharactersFragment
import com.example.rickandmortyaston.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentPlace, CharactersFragment(), null)
            .commit()
    }
}