package com.example.rickandmortyaston.presentation.locations

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyaston.databinding.ItemEpisodesBinding
import com.example.rickandmortyaston.databinding.ItemLocationsBinding
import com.example.rickandmortyaston.domain.episodes.EpisodeDomain
import com.example.rickandmortyaston.domain.locations.LocationDomain


class RecyclerAdapterLocations(
    private val context: Context,
    private val toItem: (id: Int) -> Unit,
    private val nextPage: () -> Unit
) :
    RecyclerView.Adapter<RecyclerAdapterLocations.LocationsViewHolder>() {
    var locations = mutableListOf<LocationDomain>()
    private var isPageLoading = false
    fun setList(list: List<LocationDomain>) {
        this.locations = list.toMutableList()
        notifyDataSetChanged()
        isPageLoading = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLocationsBinding.inflate(inflater)
        return LocationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        val location = locations[position]

        holder.textViewName.text = (location.name)
        holder.textViewType.text = location.type
        holder.textViewDimension.text = location.dimension
        holder.cardView.setOnClickListener {
            toItem.invoke(location.id)
        }
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    class LocationsViewHolder(binding: ItemLocationsBinding) : RecyclerView.ViewHolder(binding.root) {
        val textViewName = binding.nameTextView
        val cardView = binding.CardViewId
        val textViewType = binding.typeTextView
        val textViewDimension = binding.dimensionTextView
    }

    override fun onViewAttachedToWindow(holder: LocationsViewHolder) {
        super.onViewAttachedToWindow(holder)
        val layoutPosition = holder.layoutPosition
        if (layoutPosition == locations.size - 1 && !isPageLoading) {
            println("isPageLoading " + isPageLoading)
            isPageLoading = true
            nextPage()
        }
    }

}