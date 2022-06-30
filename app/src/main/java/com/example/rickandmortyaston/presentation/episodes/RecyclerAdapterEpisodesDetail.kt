package com.example.rickandmortyaston.presentation.episodes

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmortyaston.databinding.ItemBinding
import com.example.rickandmortyaston.domain.characters.CharacterDomain

class RecyclerAdapterEpisodesDetail(
    private val context: Context,
    private val toItem: (id: Int) -> Unit
) : RecyclerView.Adapter<RecyclerAdapterEpisodesDetail.EpisodesCharactersViewHolder>() {
    var characters = mutableListOf<CharacterDomain>()
    fun setList(list: List<CharacterDomain>) {
        this.characters = list.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):EpisodesCharactersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBinding.inflate(inflater)
        return EpisodesCharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodesCharactersViewHolder, position: Int) {
        val character = characters[position]

        holder.textViewName.text = (character.name)
        holder.textViewSpecies.text=character.species
        holder.textViewStatus.text=character.status
        holder.textViewGender.text=character.gender
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

    class EpisodesCharactersViewHolder(binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val textViewName = binding.nameTextView
        val imageView = binding.imageView
        val cardView = binding.CardViewId
        val textViewSpecies = binding.speciesTextView
        val textViewStatus = binding.statusTextView
        val textViewGender=binding.genderTextView
    }
}