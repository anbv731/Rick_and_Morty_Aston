package com.example.rickandmortyaston.data.locations

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDao {

    @Query("select * from locations WHERE id LIKE :id")
    fun getIdLocation(id: Int): LocationDBEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(locations: List<LocationDBEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(locations: LocationDBEntity)

    @Query("select * from locations WHERE name LIKE '%' || :name || '%' and type LIKE '%' || :type || '%'and dimension LIKE '%' || :dimension || '%'")
    fun searchLocations(
        name: String,
        type: String,
        dimension: String
    ): List<LocationDBEntity>

    @Query("delete from locations")
    fun deleteLocations()
}