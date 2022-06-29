package com.example.rickandmortyaston.domain.characters

class Request(
    var name: String = "",
    var status: String = "",
    var gender: String = "",
    var species: String = "",
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
enum class Type {
    Experiment,
    Superhuman,
    Parasite,
    Human,
    Fish,
    Cromulon,
    Cat,
    Bepisian,
    Hivemind,
    Mytholog,
    Dog,
    Bird,
    Korblock,
    Boobloosian,
    Elephant,
    Gromflomite,
    Centaur,
    Vampire,
    Animal,
    Robot,
    Zigerion,
    Giant,
    Alien,
    Demon,
    Shapeshifter,
    Game
}