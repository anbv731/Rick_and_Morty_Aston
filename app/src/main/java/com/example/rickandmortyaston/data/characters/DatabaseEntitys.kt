package com.example.rickandmortyaston.data.characters

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.rickandmortyaston.domain.characters.CharacterDomain

@Entity(tableName = "characters", indices = [Index(value = ["id"], unique = true)])
data class CharacterDBEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "species")
    val species: String,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "created")
    val created: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "episode")
    val episode: List<String>,
    @ColumnInfo(name = "originname")
    val originName: String,
    @ColumnInfo(name = "origin")
    val origin: String,
    @ColumnInfo(name = "locationname")
    val locationName: String,
    @ColumnInfo(name = "location")
    val location: String,
)

fun List<CharacterDBEntity>.asListDomainModel(): List<CharacterDomain> {
    return map {
        CharacterDomain(
            name = it.name,
            id = it.id,
            status = it.status,
            species = it.species,
            created = it.created,
            image = it.image,
            gender = it.gender,
            type = it.type,
            episode = it.episode,
            origin = it.origin,
            originName = it.originName,
            location = it.location,
            locationName = it.locationName

        )
    }
}

fun CharacterDBEntity.asDomainModel(): CharacterDomain {
    return CharacterDomain(
        name = this.name,
        id = this.id,
        status = this.status,
        species = this.species,
        created = this.created,
        image = this.image,
        gender = this.gender,
        type = this.type,
        episode = this.episode,
        originName = this.originName,
        origin = this.origin,
        locationName = this.locationName,
        location = this.location
    )
}