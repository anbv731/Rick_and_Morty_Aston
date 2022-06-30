package com.example.rickandmortyaston.domain.locations

data class LocationDomain(
    val id: Int,
    val name: String,
    val type:String,
    val dimension: String,
    val residents:List<String>
)
