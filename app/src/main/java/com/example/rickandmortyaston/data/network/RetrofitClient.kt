package com.example.characters.data.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitClient {
    companion object{
        private const val BASE_URL = "https://rickandmortyapi.com/api/"
    }

    fun getApi(): NetworkService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        return retrofit.create(NetworkService::class.java)
    }
}