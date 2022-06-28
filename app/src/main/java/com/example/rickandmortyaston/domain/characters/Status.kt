package com.example.rickandmortyaston.domain.characters

class Request(
    var name: String = "",
    var status: Status? = null,
    var gender: Gender? = null,
    var species: Species? = null,
    var type: String = ""
)

enum class Status {
    Dead, Alive, Unknown
}

enum class Gender {
    Male, Female, Unknown, Genderless
}

enum class Species {
    Human, Alien, Humanoid, unknown, Poopybutthole, Mythological_Creature, Animal, Robot, Cronenberg, Disease
}