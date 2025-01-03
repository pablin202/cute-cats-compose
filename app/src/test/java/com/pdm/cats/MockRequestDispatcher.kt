package com.pdm.cats

import com.google.common.io.Resources
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.File
import java.net.HttpURLConnection

class MockRequestDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "/images/search?page=0&limit=20&has_breeds=1&order=RAND" -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("catsresponse.json"))
            }

            else -> MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                .setBody("Unknown Request Path ${request.path}")
        }
    }

    private fun getJson(path: String): String {
        val uri = Resources.getResource(path)
        val file = File(uri.path)
        return file.readText(Charsets.UTF_8) // Ensures proper encoding
    }
}