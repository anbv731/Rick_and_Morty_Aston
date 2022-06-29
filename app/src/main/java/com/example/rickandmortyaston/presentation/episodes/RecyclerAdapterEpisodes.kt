package com.example.rickandmortyaston.presentation.episodes

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyaston.databinding.ItemEpisodesBinding
import com.example.rickandmortyaston.domain.episodes.EpisodeDomain


class RecyclerAdapterEpisodes(
    private val context: Context,
    private val toItem: (id: Int) -> Unit,
    private val nextPage: () -> Unit
) :
    RecyclerView.Adapter<RecyclerAdapterEpisodes.EpisodesViewHolder>() {
    var episodes = mutableListOf<EpisodeDomain>()
    private var isPageLoading = false
    fun setList(list: List<EpisodeDomain>) {
        this.episodes = list.toMutableList()
        notifyDataSetChanged()
        isPageLoading = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEpisodesBinding.inflate(inflater)
        return EpisodesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
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

    class EpisodesViewHolder(binding: ItemEpisodesBinding) : RecyclerView.ViewHolder(binding.root) {
        val textViewName = binding.nameTextView
        val cardView = binding.CardViewId
        val textViewAirDate = binding.airDateTextView
        val textViewEpisode = binding.episodeTextView
    }

    override fun onViewAttachedToWindow(holder: EpisodesViewHolder) {
        super.onViewAttachedToWindow(holder)
        val layoutPosition = holder.layoutPosition
        if (layoutPosition == episodes.size - 1 && !isPageLoading) {
            println("isPageLoading " + isPageLoading)
            isPageLoading = true
            nextPage()
        }
    }

}