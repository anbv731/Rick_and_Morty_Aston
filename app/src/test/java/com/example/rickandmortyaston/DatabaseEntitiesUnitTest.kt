package com.example.rickandmortyaston

import com.example.rickandmortyaston.data.characters.CharacterDBEntity
import com.example.rickandmortyaston.data.characters.asDomainModel
import com.example.rickandmortyaston.data.characters.asListDomainModel
import com.example.rickandmortyaston.domain.characters.CharacterDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class DatabaseEntitiesUnitTest {

    @Test
    fun asDomainModel_isCorrect() {
        val dbEntity: CharacterDBEntity =
            CharacterDBEntity(1, "", "", "", "", "", "", "", listOf(), "", "", "", "")
        val characterDomain: CharacterDomain =
            CharacterDomain(1, "", "", "", "", "", "", "", "", "", "", listOf(), "")

        assertEquals(dbEntity.asDomainModel(), characterDomain)
    }

    @Test
    fun asDomainModel_isNotCorrect() {
        val dbEntity: CharacterDBEntity =
            CharacterDBEntity(1, "Name", "Status", "", "", "", "", "", listOf(), "", "", "", "")
        val characterDomain: CharacterDomain =
            CharacterDomain(1, "", "", "", "", "", "", "", "", "", "", listOf(), "")

        assertNotEquals(dbEntity.asDomainModel(), characterDomain)
    }

    @Test
    fun asListDomainModel_isCorrect() {
        val dbEntity: CharacterDBEntity =
            CharacterDBEntity(1, "", "", "", "", "", "", "", listOf(), "", "", "", "")
        val dbEntityList: List<CharacterDBEntity> = listOf(dbEntity, dbEntity.copy(id = 2))

        val characterDomain: CharacterDomain =
            CharacterDomain(1, "", "", "", "", "", "", "", "", "", "", listOf(), "")
        val characterDomainList: List<CharacterDomain> =
            listOf(characterDomain, characterDomain.copy(id = 2))

        assertEquals(dbEntityList.asListDomainModel(), characterDomainList)
    }

    @Test
    fun asListDomainModel_isNotCorrect() {
        val dbEntity: CharacterDBEntity =
            CharacterDBEntity(1, "Name", "Status", "", "", "", "", "", listOf(), "", "", "", "")
        val dbEntityList: List<CharacterDBEntity> = listOf(dbEntity, dbEntity.copy(id = 2))

        val characterDomain: CharacterDomain =
            CharacterDomain(1, "", "", "", "", "", "", "", "", "", "", listOf(), "")
        val characterDomainList: List<CharacterDomain> =
            listOf(characterDomain, characterDomain.copy(id = 2))

        assertNotEquals(dbEntityList.asListDomainModel(), characterDomainList)
    }
}