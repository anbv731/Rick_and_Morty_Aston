package com.example.characters.data.network

import com.example.rickandmortyaston.data.characters.CharacterDto
import com.example.rickandmortyaston.data.characters.ResponseCharacters
import com.example.rickandmortyaston.data.episodes.EpisodeDto
import com.example.rickandmortyaston.data.episodes.ResponseEpisodes
import com.example.rickandmortyaston.data.locations.LocationDto
import com.example.rickandmortyaston.data.locations.ResponseLocations
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {
    @GET("character")
    suspend fun getCharactersData(
        @Query("page") page: Int?,
        @Query("name") name: String?,
        @Query("status") status: String?,
        @Query("gender") gender: String?,
        @Query("species") species: String?,
        @Query("type") type: String?
    ): ResponseCharacters

    @GET("character/{id}")
    suspend fun getSingleCharacter(
        @Path("id") id: String
    ): CharacterDto
    @GET("character/{id}")
    suspend fun getCharactersById(
        @Path("id") id: String
    ): List<CharacterDto>

    @GET("episode")
    suspend fun getEpisodesData(
        @Query("page") page: Int?,
        @Query("name") name: String?,
        @Query("episode") episode: String?
    ): ResponseEpisodes

    @GET("episode/{id}")
    suspend fun getSingleEpisode(
        @Path("id") id: String
    ): EpisodeDto
    @GET("episode/{id}")
    suspend fun getEpisodesbyId(
        @Path("id") id: String
    ): List<EpisodeDto>

    @GET("location")
    suspend fun getLocationsData(
        @Query("page") page: Int?,
        @Query("name") name: String?,
        @Query("type") type: String?,
        @Query("dimension") dimension: String?,
    ): ResponseLocations

    @GET("location/{id}")
    suspend fun getSingleLocation(
        @Path("id") id: String
    ): LocationDto
}
