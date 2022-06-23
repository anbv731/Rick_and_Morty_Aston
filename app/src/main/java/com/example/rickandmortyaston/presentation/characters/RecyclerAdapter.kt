package com.example.rickandmortyaston.presentation.characters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmortyaston.R
import com.example.rickandmortyaston.databinding.ItemBinding
import com.example.rickandmortyaston.domain.characters.CharacterDomain


class RecyclerAdapter(
    private val context: Context,
    private val toItem: (id: Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerAdapter.CharactersViewHolder>() {
    var characters = mutableListOf<CharacterDomain>()

    fun setList(list: List<CharacterDomain>) {
        this.characters = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBinding.inflate(inflater)
        return CharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val character = characters[position]

        holder.textViewName.text = (character.name)
        Glide.with(context)
            .load(character.image)
            .circleCrop()
            .into(holder.imageView)
        holder.cardView.setOnClickListener {
            toItem.invoke(character.id)
        }
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    class CharactersViewHolder(binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val textViewName = binding.nameTextView
        val imageView = binding.imageView
        val cardView = binding.CardViewId
    }
}