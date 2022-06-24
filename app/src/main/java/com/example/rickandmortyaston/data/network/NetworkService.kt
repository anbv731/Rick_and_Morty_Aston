package com.example.characters.data.network

import com.example.rickandmortyaston.domain.characters.CharacterDomain
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {
//    @GET("character")
//    suspend fun getData(): Response

    @GET("character")
    suspend fun getPageData(
        @Query("page") page:Int
    ): Response

}
