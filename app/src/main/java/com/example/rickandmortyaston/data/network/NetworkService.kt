package com.example.characters.data.network

import com.example.rickandmortyaston.data.characters.ResponseCharacters
import com.example.rickandmortyaston.data.episodes.ResponseEpisodes
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("character")
    suspend fun getCharactersData(
        @Query("page") page:Int?,
        @Query("name") name:String?,
        @Query("status") status: String?,
        @Query("gender") gender:String?,
       @Query("species") species: String?,
        @Query("type") type:String?
    ): ResponseCharacters
    @GET("episode")
    suspend fun getEpisodesData(
        @Query("page") page:Int?,
        @Query("name") name:String?,
        @Query("episode") episode: String?
    ): ResponseEpisodes
}
