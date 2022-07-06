package com.example.rickandmortyaston

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.rickandmortyaston.domain.characters.CharacterDomain
import com.example.rickandmortyaston.domain.characters.use_cases.GetDBCharacterUseCase
import com.example.rickandmortyaston.domain.episodes.EpisodeDomain
import com.example.rickandmortyaston.domain.episodes.usecases.GetDBEpisodeUseCase
import com.example.rickandmortyaston.domain.episodes.usecases.GetEpisodeUseCase
import com.example.rickandmortyaston.presentation.characters.CharacterDetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class CharacterDetailViewModelTest {
    val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    lateinit var getDBCharacterUseCase: GetDBCharacterUseCase
    lateinit var getEpisodeUseCase: GetEpisodeUseCase
    lateinit var getDBEpisodeUseCase: GetDBEpisodeUseCase

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    val character = CharacterDomain(
        6,
        "Abadango Cluster Princess",
        "Alive",
        "Alien",
        "Female",
        "2017-11-04T19:50:28.250Z",
        "",
        "2",
        "Abadango",
        "2",
        "Abadango",
        listOf("27"),
        "https://rickandmortyapi.com/api/character/avatar/6.jpeg"
    )
    val listEpisodes = listOf(EpisodeDomain(1, "", "", "", emptyList()))
    val exception = Exception("some text")

    @Before
    fun init() {
        getDBCharacterUseCase = mockk<GetDBCharacterUseCase>()
        getEpisodeUseCase = mockk<GetEpisodeUseCase>()
        getDBEpisodeUseCase = mockk<GetDBEpisodeUseCase>()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetData() = runBlockingTest {
        val expected = character

        coEvery { getDBCharacterUseCase.execute(6) } returns (expected)
        val viewModel = CharacterDetailViewModel(
            getDBCharacterUseCase,
            getEpisodeUseCase,
            getDBEpisodeUseCase,
            dispatcher
        )
        viewModel.getData(6)
        val result = viewModel.character.value

        assertEquals(expected, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetDataExeption() = runBlockingTest {
        val expected = exception
        coEvery { getDBCharacterUseCase.execute(6) }.throws(expected)
        val viewModel = CharacterDetailViewModel(
            getDBCharacterUseCase,
            getEpisodeUseCase,
            getDBEpisodeUseCase,
            dispatcher
        )

        viewModel.getData(6)
        val result = viewModel.errorMessage.value

        assertEquals(expected.message, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetEpisode() = runBlockingTest {
        val expected = listEpisodes
        coEvery { getEpisodeUseCase.execute(listOf(27)) } returns (expected)
        val viewModel = CharacterDetailViewModel(
            getDBCharacterUseCase,
            getEpisodeUseCase,
            getDBEpisodeUseCase,
            dispatcher
        )

        viewModel.getEpisodes(character)
        val result = viewModel.episodes.value

        assertEquals(expected, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetEpisodesExeption() = runBlockingTest {
        val expected = exception
        coEvery { getEpisodeUseCase.execute(listOf(27)) }.throws(expected)
        val viewModel = CharacterDetailViewModel(
            getDBCharacterUseCase,
            getEpisodeUseCase,
            getDBEpisodeUseCase,
            dispatcher
        )

        viewModel.getEpisodes(character)
        val result = viewModel.errorMessage.value

        assertEquals(expected.message, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetDBEpisode() = runBlockingTest {
        val expected = listEpisodes
        coEvery { getEpisodeUseCase.execute(listOf(27)) }.throws(exception)
        coEvery { getDBEpisodeUseCase.execute(27) } returns listEpisodes[0]
        val viewModel = CharacterDetailViewModel(
            getDBCharacterUseCase,
            getEpisodeUseCase,
            getDBEpisodeUseCase,
            dispatcher
        )

        viewModel.getEpisodes(character)
        val result = viewModel.episodes.value

        assertEquals(expected, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetDBEpisodesExeption() = runBlockingTest {
        val expected = exception
        coEvery { getEpisodeUseCase.execute(listOf(27)) }.throws(exception)
        coEvery { getDBEpisodeUseCase.execute(27) } throws (exception)
        val viewModel = CharacterDetailViewModel(
            getDBCharacterUseCase,
            getEpisodeUseCase,
            getDBEpisodeUseCase,
            dispatcher
        )

        viewModel.getEpisodes(character)
        val result = viewModel.errorMessage.value

        assertEquals(expected.message, result)
    }
}