package com.example.rickandmortyaston.data.database

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class MyTypeConverter {
    private val moshi = Moshi.Builder().build()
    private val stringType = Types.newParameterizedType(List::class.java, String::class.java)
    private val membersAdapter = moshi.adapter<List<String>>(stringType)

    @TypeConverter
    fun listToJson(value: List<String>) = membersAdapter.toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = membersAdapter.fromJson(value)
}