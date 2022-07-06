package com.example.rickandmortyaston.presentation.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyaston.databinding.ItemEpisodesBinding
import com.example.rickandmortyaston.domain.episodes.EpisodeDomain

class RecyclerAdapterCharactersDetail(
    private val toItem: (id: Int) -> Unit
) : RecyclerView.Adapter<RecyclerAdapterCharactersDetail.CharactersDetailViewHolder>() {
    var episodes = mutableListOf<EpisodeDomain>()
    fun setList(list: List<EpisodeDomain>) {
        this.episodes = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEpisodesBinding.inflate(inflater)
        return CharactersDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersDetailViewHolder, position: Int) {
        val episode = episodes[position]

        holder.textViewName.text = (episode.name)
        holder.textViewAirDate.text = episode.airDate
        holder.textViewEpisode.text = episode.episode

        holder.cardView.setOnClickListener {
            toItem.invoke(episode.id)
        }
    }

    override fun getItemCount(): Int {
        return episodes.size
    }

    class CharactersDetailViewHolder(binding: ItemEpisodesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val textViewName = binding.nameTextView
        val cardView = binding.CardViewId
        val textViewAirDate = binding.airDateTextView
        val textViewEpisode = binding.episodeTextView
    }
}
