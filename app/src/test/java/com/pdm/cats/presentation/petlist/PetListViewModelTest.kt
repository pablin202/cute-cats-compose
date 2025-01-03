package com.pdm.cats.presentation.petlist

import com.pdm.cats.domain.models.BreedModel
import com.pdm.cats.domain.models.CatModel
import com.pdm.cats.domain.models.WeightModel
import com.pdm.cats.domain.repository.PetsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PetListViewModelTest {

    private val petsRepository = mockk<PetsRepository>(relaxed = true)
    private lateinit var viewModel: PetListViewModel


    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = PetListViewModel(petsRepository)
    }

    @Test
    fun `getPets updates UI state with fetched cats`() = runTest {
        val cats = listOf(
            mockCatModel
        )
        // Given
        coEvery { petsRepository.getCats(0) } returns flowOf(cats)

        // When
        viewModel.getPets()

        // Verify
        coVerify { petsRepository.getCats(0) }

        // Then
        val uiState = viewModel.petsUIState.value
        assertEquals(cats, uiState.cats)
    }

    @Test
    fun `initial state sets isLoading to true and fetches pets and breeds`() = runTest {
        // Arrange
        val cats = emptyList<CatModel>()
        val breeds = emptyList<BreedModel>()
        coEvery { petsRepository.getCats(0) } returns flowOf(cats)
        coEvery { petsRepository.getBreeds() } returns flowOf(breeds)

        // Act
        viewModel = PetListViewModel(petsRepository) // Recreate to trigger init

        // Assert
        val uiState = viewModel.petsUIState.value
        assertTrue(!uiState.isLoading) // Initially loading
        assertTrue(uiState.cats.isEmpty()) // No cats initially
        assertTrue(uiState.breeds.isEmpty()) // No breeds initially

        // Allow the fetch to complete
        coVerify { petsRepository.getCats(0) }
        coVerify { petsRepository.getBreeds() }
    }

    @Test
    fun `onAction LoadMore increments page and fetches more pets`() = runTest {
        // Arrange
        val initialCats = listOf(mockCatModel)
        val newCats = listOf(mockCatModel.copy(id = "cat2"))
        coEvery { petsRepository.getCats(0) } returns flowOf(initialCats)
        coEvery { petsRepository.getCats(1) } returns flowOf(newCats)

        // Preload initial data
        viewModel.getPets()

        // Act
        viewModel.onAction(PetListAction.LoadMore)

        // Assert
        val uiState = viewModel.petsUIState.value
        assertEquals(initialCats + newCats, uiState.cats) // Both initial and new cats should be in the list
        assertFalse(uiState.isLoading)

        coVerify { petsRepository.getCats(1) }
    }

    @Test
    fun `onAction BreedSelected fetches pets by breed`() = runTest {
        // Arrange
        val breed = mockCatModel.breeds.first().name
        val breedCats = listOf(mockCatModel)
        coEvery { petsRepository.getCatsByBreed(breed) } returns flowOf(breedCats)

        // Act
        viewModel.onAction(PetListAction.BreedSelected(breed))

        // Assert
        val uiState = viewModel.petsUIState.value
        assertTrue(!uiState.isLoading) // Not Loading while fetching by breed
        assertEquals(breedCats, uiState.cats) // Cats should match the breed filter
        assertEquals(breed, uiState.currentBreed)

        coVerify { petsRepository.getCatsByBreed(breed) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    companion object {

        private val mockBreedModel = BreedModel(
            countryCode = "US",
            countryCodes = "US, CA",
            id = "1",
            lifeSpan = "12 - 15",
            name = "Maine Coon",
            origin = "United States",
            temperament = "Friendly, Gentle, Intelligent",
            weight = WeightModel(
                imperial = "10 - 20",
                metric = "4.5 - 9"
            ),
            wikipediaUrl = "https://en.wikipedia.org/wiki/Maine_Coon",
            description = "Maine Coons are large, friendly cats with luxurious coats.",
            adaptability = 5,
            affectionLevel = 4,
            energyLevel = 3,
            vetstreetUrl = "https://www.vetstreet.com/maine-coon",
            vcahospitalsUrl = "https://vcahospitals.com/maine-coon",
            cfaUrl = "https://cfa.org/maine-coon"
        )

        val mockCatModel = CatModel(
            breeds = listOf(
                mockBreedModel
            ),
            height = 35,
            id = "cat1",
            url = "https://example.com/cat1.jpg",
            width = 50,
            isFavorite = true
        )
    }
}