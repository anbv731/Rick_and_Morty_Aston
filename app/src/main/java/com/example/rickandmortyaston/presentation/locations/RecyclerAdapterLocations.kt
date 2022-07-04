package com.example.rickandmortyaston.presentation.locations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyaston.databinding.ItemLocationsBinding
import com.example.rickandmortyaston.domain.locations.LocationDomain


class RecyclerAdapterLocations(
    private val toItem: (id: Int) -> Unit,
) :
    RecyclerView.Adapter<RecyclerAdapterLocations.LocationsViewHolder>() {
    var locations = mutableListOf<LocationDomain>()

    fun setList(list: List<LocationDomain>) {
        this.locations = list.toMutableList()
        notifyDataSetChanged()

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

    class LocationsViewHolder(binding: ItemLocationsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val textViewName = binding.nameTextView
        val cardView = binding.CardViewId
        val textViewType = binding.typeTextView
        val textViewDimension = binding.dimensionTextView
    }

}