package com.example.rickandmortyaston.domain.characters

data class CharacterDomain(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val created: String,
    val type: String,
  //  val location:String,
   // val origin: String,
    val episode:List<String>,
    val image: String
)