package com.example.characters.data.network

import com.example.rickandmortyaston.domain.characters.Gender
import com.example.rickandmortyaston.domain.characters.Species
import com.example.rickandmortyaston.domain.characters.Status
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
//    @GET("character")
//    suspend fun getData(): Response

    @GET("character")
    suspend fun getPageData(
        @Query("page") page:Int?,
        @Query("name") name:String?,
        @Query("status") status: Status?,
        @Query("gender") gender:Gender?,
       @Query("species") species: Species?,
        @Query("type") type:String?
    ): Response

}
