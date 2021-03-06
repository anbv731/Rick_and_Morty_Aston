package com.example.rickandmortyaston.data.episodes

import android.content.Context
import com.example.characters.data.database.getDatabase
import com.example.characters.data.network.RetrofitClient
import com.example.rickandmortyaston.R
import com.example.rickandmortyaston.domain.episodes.EpisodeDomain
import com.example.rickandmortyaston.domain.episodes.EpisodesRepository
import com.example.rickandmortyaston.domain.episodes.RequestEpisodes
import javax.inject.Inject

class EpisodesRepositoryImpl @Inject constructor(
    private val context: Context,
) : EpisodesRepository {
    private val database = getDatabase(context)
    private var page = 1
    private var maxPage = 1


    override suspend fun getEpisodes(
        refresh: Boolean,
        nextPage: Boolean,
        request: RequestEpisodes
    ): List<EpisodeDomain> {
        if (!nextPage) {
            page = 1
            val result =
                RetrofitClient().getApi().getEpisodesData(page, request.name, request.episode)
            maxPage = result.info.pages.toInt()
            println(maxPage)
            val episodes = result.results
            if (refresh) database.episodesDao.deleteEpisodes()
            database.episodesDao.insertAll(episodes.asModel())
            return database.episodesDao.searchEpisodes(request.name, request.episode)
                .asListDomainModel()
        } else if (page < maxPage) {
            page++
            val result =
                RetrofitClient().getApi().getEpisodesData(page, request.name, request.episode)
            val episodes = result.results
            database.episodesDao.insertAll(episodes.asModel())
            return database.episodesDao.searchEpisodes(request.name, request.episode)
                .asListDomainModel()
        } else {
            throw Exception(context.getString(R.string.theEnd))
        }
    }

    override suspend fun getEpisode(id: List<Int>): List<EpisodeDomain> {
        if (id.size == 1) {
            val result = RetrofitClient().getApi().getSingleEpisode(id[0].toString())
            database.episodesDao.insertOne(result.asModelOne())
            return listOf(result.asModelOne().asDomainModel())
        } else {
            val result = RetrofitClient().getApi().getEpisodesbyId(id.toString())
            database.episodesDao.insertAll(result.asModel())
            return result.asModel().asListDomainModel()
        }
    }

    override suspend fun getDBEpisode(id: Int): EpisodeDomain {
        val result = database.episodesDao.getIdEpisode(id)
        return database.episodesDao.getIdEpisode(id)?.asDomainModel()
    }

    override suspend fun getDBEpisodes(request: RequestEpisodes): List<EpisodeDomain> {
        return database.episodesDao.searchEpisodes(request.name, request.episode)
            .asListDomainModel()
    }
}
