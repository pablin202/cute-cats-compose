package com.pdm.cats

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pdm.cats.data.networking.CatsApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.mockwebserver.MockWebServer
import okhttp3.MediaType.Companion.toMediaType
import okio.IOException
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class CatsAPITest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var catsApi: CatsApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = MockRequestDispatcher() // Custom dispatcher
        mockWebServer.start()

        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/")) // Use mock server's URL
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()

        catsApi = retrofit.create(CatsApi::class.java)
    }

    @Test
    fun `fetchCats() returns a list of cats`() = runTest {
        // Call the API
        val response = catsApi.fetchCats("0")

        // Assertions
        assert(response.isSuccessful)
        assert(response.body()?.isNotEmpty() == true)
    }

    @Test
    fun `fetchCats() with invalid query parameter should fail`() = runTest {
        // Simulate a request with an invalid query parameter
        val response = catsApi.fetchCats("invalid_page")

        // This should fail because MockRequestDispatcher doesn't handle this case
        assert(!response.isSuccessful) // This will fail because response is NOT successful
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        mockWebServer.shutdown()
    }
}