package com.pdm.cats

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pdm.cats.data.local.dao.BreedDao
import com.pdm.cats.data.local.database.CatDatabase
import com.pdm.cats.data.local.entity.BreedEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BreedDaoTest {
    private lateinit var database: CatDatabase
    private lateinit var breedDao: BreedDao

    @Before
    fun createDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CatDatabase::class.java
        ).build()
        breedDao = database.breedDao()
    }

    @Test
    fun insertBreed_andRetrieve() = runTest {
        // Given
        val breed = Companion.BREED_ENTITY

        // When
        breedDao.insertBreed(breed)
        val breeds = breedDao.getAllBreads().first()

        // Then
        assert(breeds.isNotEmpty())
        assert(breeds.size == 1)
        assert(breeds[0] == breed)
    }

    @Test
    fun insertBreeds_andRetrieve() = runTest {
        // Given
        val breeds = listOf(
            BREED_ENTITY,
            BREED_ENTITY_2
        )

        // When
        breedDao.insertBreeds(breeds)
        val retrievedBreeds = breedDao.getAllBreads().first()

        // Then
        assert(retrievedBreeds.size == breeds.size)
        assert(retrievedBreeds.containsAll(breeds))
    }

    @Test
    fun insertBreed_replacesExisting() = runTest {
        // Given
        val breed = BREED_ENTITY

        val updatedBreed = breed.copy(name = "Updated Abyssinian")

        // When
        breedDao.insertBreed(breed)
        breedDao.insertBreed(updatedBreed)
        val breeds = breedDao.getAllBreads().first()

        // Then
        assert(breeds.size == 1)
        assert(breeds[0] == updatedBreed)
    }

    @Test
    fun getAllBreeds_returnsEmptyList() = runTest {
        // When
        val breeds = breedDao.getAllBreads().first()

        // Then
        assert(breeds.isEmpty())
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    companion object {
        val BREED_ENTITY = BreedEntity(
            id = "1",
            name = "Abyssinian",
            origin = "Ethiopia",
            description = "A beautiful, agile cat known for its active and playful nature.",
            temperament = "Active, Energetic, Intelligent, Gentle",
            countryCode = "ET",
            countryCodes = "ET",
            lifeSpan = "14 - 15",
            weightImperial = "7 - 10",
            weightMetric = "3 - 5",
            wikipediaUrl = "https://en.wikipedia.org/wiki/Abyssinian_(cat)",
            adaptability = 5,
            affectionLevel = 5,
            energyLevel = 5,
            vetstreetUrl = "http://www.vetstreet.com/cats/abyssinian",
            vcahospitalsUrl = "https://vcahospitals.com/know-your-pet/cat-breeds/abyssinian",
            cfaUrl = "http://cfa.org/Breeds/BreedsAB/Abyssinian.aspx"
        )

        val BREED_ENTITY_2 = BreedEntity(
            id = "2",
            name = "Abyssinian",
            origin = "Ethiopia",
            description = "A beautiful, agile cat known for its active and playful nature.",
            temperament = "Active, Energetic, Intelligent, Gentle",
            countryCode = "ET",
            countryCodes = "ET",
            lifeSpan = "14 - 15",
            weightImperial = "7 - 10",
            weightMetric = "3 - 5",
            wikipediaUrl = "https://en.wikipedia.org/wiki/Abyssinian_(cat)",
            adaptability = 5,
            affectionLevel = 5,
            energyLevel = 5,
            vetstreetUrl = "http://www.vetstreet.com/cats/abyssinian",
            vcahospitalsUrl = "https://vcahospitals.com/know-your-pet/cat-breeds/abyssinian",
            cfaUrl = "http://cfa.org/Breeds/BreedsAB/Abyssinian.aspx"
        )
    }
}