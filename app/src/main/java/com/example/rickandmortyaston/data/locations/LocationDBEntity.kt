package com.example.rickandmortyaston.data.locations

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.rickandmortyaston.domain.locations.LocationDomain

@Entity(tableName = "locations", indices = [Index(value = ["id"], unique = true)])
data class LocationDBEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "dimension")
    val dimension: String,
    @ColumnInfo(name = "residents")
    val residents: List<String>
)

fun List<LocationDBEntity>.asListDomainModel(): List<LocationDomain> {
    return map {
        LocationDomain(
            name = it.name,
            id = it.id,
            type = it.type,
            dimension = it.dimension,
            residents = it.residents
        )
    }
}

fun LocationDBEntity.asDomainModel(): LocationDomain {
    return LocationDomain(
        name = this.name,
        id = this.id,
        type = this.type,
        dimension = this.dimension,
        residents = this.residents
    )
}