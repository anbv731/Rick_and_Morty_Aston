package com.example.rickandmortyaston.data.locations

import android.content.Context
import com.example.characters.data.database.getDatabase
import com.example.characters.data.network.RetrofitClient
import com.example.rickandmortyaston.R
import com.example.rickandmortyaston.domain.locations.LocationDomain
import com.example.rickandmortyaston.domain.locations.LocationRepository
import com.example.rickandmortyaston.domain.locations.RequestLocation
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    private val context: Context,
) : LocationRepository {
    private val database = getDatabase(context)
    private var page = 1
    private var maxPage = 1


    override suspend fun getLocations(
        refresh: Boolean,
        nextPage: Boolean,
        request: RequestLocation
    ): List<LocationDomain> {
        if (!nextPage) {
            page = 1
            val result = RetrofitClient().getApi()
                .getLocationsData(page, request.name, request.type, request.dimension)
            maxPage = result.info.pages.toInt()
            println(maxPage)
            val locations = result.results
            if (refresh) database.locationDao.deleteLocations()
            database.locationDao.insertAll(locations.asModel())
            return database.locationDao.searchLocations(
                request.name,
                request.type,
                request.dimension
            ).asListDomainModel()
        } else if (page < maxPage) {
            page++
            val result = RetrofitClient().getApi()
                .getLocationsData(page, request.name, request.type, request.dimension)
            val locations = result.results
            database.locationDao.insertAll(locations.asModel())
            return database.locationDao.searchLocations(
                request.name,
                request.type,
                request.dimension
            ).asListDomainModel()
        } else {
            throw Exception(context.getString(R.string.theEnd))
        }
    }

    override suspend fun getLocation(id: Int): LocationDomain {
        val result = RetrofitClient().getApi().getSingleLocation(id.toString())
        database.locationDao.insertOne(result.asModelOne())
        return result.asModelOne().asDomainModel()
    }

    override suspend fun getDBLocation(id: Int): LocationDomain {
        return database.locationDao.getIdLocation(id).asDomainModel()
    }

    override suspend fun getDBLocations(request: RequestLocation): List<LocationDomain> {
        return database.locationDao.searchLocations(request.name, request.type, request.dimension)
            .asListDomainModel()
    }
}
