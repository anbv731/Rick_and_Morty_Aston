package com.example.characters.data.network

import retrofit2.http.GET

interface NetworkService {
    @GET("character")
    suspend fun getData(): Response
}